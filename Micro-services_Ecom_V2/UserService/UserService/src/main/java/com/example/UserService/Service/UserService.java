package com.example.UserService.Service;

import com.example.UserService.DOA.UserRepo;

import com.example.UserService.DTO.AddressResponse;
import com.example.UserService.DTO.UserLoginRequest;
import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.GlobalExceptionHandler.ResourceNotFoundException;
import com.example.UserService.Model.User;
import com.example.UserService.Model.UserAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final StreamBridge streamBridge;

    private final KeyCloakAdminService keyCloakAdminService;


    public List<UserResponse> fetchAllUsers(){

        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public String createUser(UserRequest userRequest){

        String token = keyCloakAdminService.getAdminAccessToken();
        String keycloakUserId = keyCloakAdminService.createUser(token,userRequest);
        keyCloakAdminService.assignRoleToUser(String.valueOf(userRequest.getUserRole()),keycloakUserId);

        User user = new User();
        updateUserFromRequest(user,userRequest);
        user.setKeycloakId(keycloakUserId);

        userRepo.save(user);

        streamBridge.send("createUser-out-0", user);

        return "User Added";
    }

    public UserResponse getUserById(Long id) {
        Optional<User> user =  userRepo.findById(id);
        return user.map(this::mapToUserResponse).orElse(null);

    }

    public String getUserBySellerName(String sellerName) {
        User user = userRepo.findByUsername(sellerName);
        if (user == null){
            throw new ResourceNotFoundException("Seller not found with username: " + sellerName);
        }
        return user.getEmail();
    }

    public UserRequest updateUser(Long id, UserRequest userRequest) {
        return userRepo.findById(id).map(existingUser -> {

                    updateUserFromRequest(existingUser, userRequest);
                    userRepo.save(existingUser);

                    String adminToken = keyCloakAdminService.getAdminAccessToken();

                    keyCloakAdminService.updateUserInKeycloak(adminToken, existingUser.getKeycloakId(), userRequest);

                    if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                        keyCloakAdminService.updateUserPasswordInKeycloak(adminToken, existingUser.getKeycloakId(), userRequest.getPassword());
                    }

                    return userRequest;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. User not found with ID: " + id));
    }


    private UserResponse mapToUserResponse(User user){

        UserResponse userResponse = new UserResponse();
        
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setRole(user.getRole());
        userResponse.setMobileNo(user.getMobileNo());
        userResponse.setEmail(user.getEmail());

        if(user.getUserAddress()!= null){
            AddressResponse addressResponse = getAddressResponse(user);
            userResponse.setAddressResponse(addressResponse);
        }

        return userResponse;

    }

    private static AddressResponse getAddressResponse(User user) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setHouseNo(user.getUserAddress().getHouseNo());
        addressResponse.setCity(user.getUserAddress().getCity());
        addressResponse.setCountry(user.getUserAddress().getCountry());
        addressResponse.setStreet(user.getUserAddress().getStreet());
        addressResponse.setState(user.getUserAddress().getState());
        addressResponse.setPincode(user.getUserAddress().getPincode());
        return addressResponse;
    }


    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setMobileNo(userRequest.getMobileNo());
        user.setRole(userRequest.getUserRole());
        if (userRequest.getAddressResponse() != null) {
            UserAddress address = user.getUserAddress();
            if (address == null) {
                address = new UserAddress();
            }

            address.setHouseNo(userRequest.getAddressResponse().getHouseNo());
            address.setCity(userRequest.getAddressResponse().getCity());
            address.setPincode(userRequest.getAddressResponse().getPincode());
            address.setState(userRequest.getAddressResponse().getState());
            address.setStreet(userRequest.getAddressResponse().getStreet());
            address.setCountry(userRequest.getAddressResponse().getCountry());

            user.setUserAddress(address);
        }

    }

    public String loginUser(UserLoginRequest userLoginRequest) {
        return keyCloakAdminService.login(userLoginRequest.getUsername(),userLoginRequest.getPassword());
    }

    public String getUserIdByKeyCloak(String keycloakId) {
        User user = userRepo.findByKeycloakId(keycloakId);
        if (user == null){
            throw new ResourceNotFoundException("User not found with Keycloak ID: " + keycloakId);
        }
        return String.valueOf(user.getId());

    }

    public String getUserFullNameById(Long id) {
        Optional<User> user =  userRepo.findById(id);
        return user.get().getFirstName() + " "+ user.get().getLastName();

    }

    public UserAddress getUserAddressById(Long id) {
        Optional<User> user =  userRepo.findById(id);
        return user.get().getUserAddress();
    }

    public String getUserEmail(Long id) {
        Optional<User> user =  userRepo.findById(id);
        return user.get().getEmail();
    }
}



package com.example.UserService.Service;

import com.example.UserService.DOA.UserRepo;
import com.example.UserService.DTO.AddressResponse;
import com.example.UserService.DTO.UserLoginRequest;
import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
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

        User user = new User();
        updateUserFromRequest(user,userRequest);
        user.setKeycloakId(keycloakUserId);

        keyCloakAdminService.assignRealmRoleTUser(userRequest.getUsername(), String.valueOf(user.getRole()),keycloakUserId);
        userRepo.save(user);
        streamBridge.send("createUser-out-0", user);
        return "User Added";
    }

    public Optional<UserResponse> getUserById(String id) {
        return userRepo.findById(id).map(this::mapToUserResponse);
    }

    public String getUserBySellerName(String sellerName) {
        return userRepo.findByUsername(sellerName).getEmail();
    }

    public UserRequest updateUser(String id, UserRequest userRequest) {
        return userRepo.findById(id)
                .map(existinguser -> {
                    updateUserFromRequest(existinguser,userRequest);
                    userRepo.save(existinguser);
                    return userRequest;
                }).orElse(null);
    }

    private UserResponse mapToUserResponse(User user){

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
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
        return user.getId();
    }
}



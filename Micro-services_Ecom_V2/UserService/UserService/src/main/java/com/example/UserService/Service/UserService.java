package com.example.UserService.Service;

import com.example.UserService.DOA.UserRepo;
import com.example.UserService.DTO.AddressResponse;
import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Model.User;
import com.example.UserService.Model.UserAddress;
import com.example.UserService.Model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {


    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResponse> fetchAllUsers(){

        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public String createUser(UserRequest userRequest){

        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepo.save(user);
        return "User Added";
    }



    public Optional<UserResponse> getUserById(String id) {
        return userRepo.findById(id).map(this::mapToUserResponse);
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
        user.setEmail(userRequest.getEmail());
        user.setMobileNo(userRequest.getMobileNo());
        user.setRole(UserRole.USER);
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
}



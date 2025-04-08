package com.kirtesh.ecommerce.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress()
        );
    }
}
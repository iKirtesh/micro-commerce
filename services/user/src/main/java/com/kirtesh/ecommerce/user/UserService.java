package com.kirtesh.ecommerce.user;

import com.kirtesh.ecommerce.exception.UserNotFoundException;
import com.mysql.cj.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public int createUser(UserRequest request) {
        var user = userRepository.save(userMapper.toUser(request));
        return user.getId();
    }

    public void updateUser(UserRequest request) {
        var user = userRepository.findById(Long.valueOf(request.id()))
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot update user : No user found with the provided ID %s", request.id())));
        mergeUser(user, request);
        userRepository.save(user);
    }

    private void mergeUser(User user, UserRequest request) {
        if (StringUtils.isNullOrEmpty(request.firstName())) {
            user.setFirstName(request.firstName());
        }
        if (StringUtils.isNullOrEmpty(request.lastName())) {
            user.setLastName(request.lastName());
        }
        if (StringUtils.isNullOrEmpty(request.email())) {
            user.setEmail(request.email());
        }
        if (request.address() != null) {
            user.setAddress(request.address());
        }
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList());
    }

    public Boolean existisById(String userId) {
        return userRepository.findById(Long.valueOf(userId)).isPresent();
    }

    public UserResponse findById(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(userMapper::fromUser)
                .orElseThrow(() -> new UserNotFoundException(String.format("No user found with the provided ID %s", userId)));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(Long.valueOf(userId));
    }
}

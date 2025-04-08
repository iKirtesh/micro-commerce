package com.kirtesh.ecommerce.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserRequest request) {
        userService.updateUser(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/exist/{user-id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok(userService.existisById(userId));
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> delete(@PathVariable("user-id") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}



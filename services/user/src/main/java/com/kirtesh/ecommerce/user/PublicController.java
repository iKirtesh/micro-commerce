package com.kirtesh.ecommerce.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/")
    public String userService() {
        return "User Service is up and running";
    }
}

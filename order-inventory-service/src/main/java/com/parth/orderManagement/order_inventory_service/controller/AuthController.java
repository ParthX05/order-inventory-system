package com.parth.orderManagement.order_inventory_service.controller;


import com.parth.orderManagement.order_inventory_service.dto.RegisterRequest;
import com.parth.orderManagement.order_inventory_service.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        authService.register(request);
        return "User registered successfully";
    }
}

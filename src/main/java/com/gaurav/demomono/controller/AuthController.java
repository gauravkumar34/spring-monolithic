package com.gaurav.demomono.controller;

import com.gaurav.demomono.dto.AuthenticationResponse;
import com.gaurav.demomono.dto.LoginRequest;
import com.gaurav.demomono.dto.RegisterRequest;
import com.gaurav.demomono.model.User;
import com.gaurav.demomono.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

//    @GetMapping("/v1/user")
//    public String hel(){
//        return "hello from auth";
//    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("hello");
        return authService.login(loginRequest);
    }

}

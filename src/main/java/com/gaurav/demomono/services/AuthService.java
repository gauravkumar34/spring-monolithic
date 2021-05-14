package com.gaurav.demomono.services;

import com.gaurav.demomono.config.BcryptGenerator;
import com.gaurav.demomono.dto.AuthenticationResponse;
import com.gaurav.demomono.dto.LoginRequest;
import com.gaurav.demomono.dto.RegisterRequest;
import com.gaurav.demomono.exception.SpringException;
import com.gaurav.demomono.model.User;
import com.gaurav.demomono.repository.UserRepository;
import com.gaurav.demomono.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private BcryptGenerator bcryptGenerator;
    private final AuthenticationManager authenticationManager;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(bcryptGenerator.passwordEncoder(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest){
        Optional<User> userEE = userRepository.findByUsername(loginRequest.getUsername());
        String ePass = userEE.get().getPassword();
        System.out.println(ePass);
        Boolean t = bcryptGenerator.passwordDecoder(loginRequest.getPassword(),ePass);

        if(t){

            return new ResponseEntity<>(jwtAuthenticationFilter.generatorJwtToken(loginRequest), HttpStatus.OK);
        }
        throw new SpringException("invalid password or username");
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }




}

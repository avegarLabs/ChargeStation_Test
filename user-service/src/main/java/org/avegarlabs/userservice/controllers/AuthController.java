package org.avegarlabs.userservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.userservice.dto.AuthRequest;
import org.avegarlabs.userservice.dto.JwtTokenResponse;
import org.avegarlabs.userservice.dto.UserListItem;
import org.avegarlabs.userservice.dto.UserModel;
import org.avegarlabs.userservice.services.JwtService;
import org.avegarlabs.userservice.services.UserService;
import org.avegarlabs.userservice.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
@Slf4j
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<Object> saveUser(@RequestBody UserModel model) {
        try {
            UserListItem item = service.persistUser(model);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
       try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                return ResponseEntity.status(HttpStatus.OK).body(new JwtTokenResponse(token));
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
        try {
            jwtService.tokenValidation(token);
            return ResponseEntity.status(HttpStatus.OK).body("Token is valid");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Object> activeUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String token = null;
            UserListItem userListItem = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                jwtService.tokenValidation(token);
                String username = jwtService.extractUserName(token);
                userListItem = service.getByName(username);
            }
            return ResponseEntity.status(HttpStatus.OK).body(userListItem);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }


}

package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.samgtu.tasker.api.dto.request.LoginRequestDTO;
import ru.samgtu.tasker.api.exception.UsernameAlreadyExistsException;
import ru.samgtu.tasker.service.AuthenticationService;
import ru.samgtu.tasker.api.dto.request.RegistrationRequestDTO;
import ru.samgtu.tasker.api.dto.response.RegistrationResponseDTO;
import ru.samgtu.tasker.api.dto.response.LoginResponseDTO;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController  {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registerCustomerUser(@RequestBody RegistrationRequestDTO body) throws UsernameAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.registerUser(body));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO body) throws AuthenticationException {
        return ResponseEntity.ok(authenticationService.loginUser(body.getUsername(), body.getPassword()));
    }
}
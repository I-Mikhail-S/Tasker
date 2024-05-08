package ru.samgtu.tasker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.samgtu.tasker.api.dto.request.RegistrationRequestDTO;
import ru.samgtu.tasker.api.dto.response.LoginResponseDTO;
import ru.samgtu.tasker.api.dto.response.RegistrationResponseDTO;
import ru.samgtu.tasker.api.exception.UsernameAlreadyExistsException;
import ru.samgtu.tasker.entity.UserEntity;
import ru.samgtu.tasker.mapper.UserMapper;
import ru.samgtu.tasker.repository.AuthorityRepository;
import ru.samgtu.tasker.repository.UserRepository;

import java.util.HashSet;
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository authorityRepository;
    @Autowired
    public AuthenticationService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.authorityRepository = authorityRepository;
    }


    public RegistrationResponseDTO registerUser(RegistrationRequestDTO request) throws UsernameAlreadyExistsException, NullPointerException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        UserEntity userEntity = userMapper.toEntity(request);

        userEntity.setAuthorities(
                new HashSet<>() {{
                    add(authorityRepository.findByAuthority("USER").get());
                }}
        );
        userRepository.save(userEntity);
        return new RegistrationResponseDTO(userEntity.getUsername());
    }
    public LoginResponseDTO loginUser(String username, String password) throws AuthenticationException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = tokenService.generateJwt(auth);

        UserEntity userEntity = userRepository.findByUsername(username).get();

        return new LoginResponseDTO(userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), token);
    }
}

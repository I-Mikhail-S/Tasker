package ru.samgtu.tasker.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.samgtu.tasker.api.dto.request.RegistrationRequestDTO;
import ru.samgtu.tasker.api.dto.response.RegistrationResponseDTO;
import ru.samgtu.tasker.api.exception.UsernameAlreadyExistsException;
import ru.samgtu.tasker.api.type.AuthorityType;
import ru.samgtu.tasker.entity.AuthorityEntity;
import ru.samgtu.tasker.entity.UserEntity;
import ru.samgtu.tasker.repository.AuthorityRepository;
import ru.samgtu.tasker.repository.UserRepository;
import ru.samgtu.tasker.service.AuthenticationService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Component
public class AuthoritiesSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthoritiesSeeder(AuthorityRepository authorityRepository, UserRepository userRepository, AuthenticationService authenticationService) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadAuthorities();

        try {
            this.loadSuperAdminUser();
        } catch (UsernameAlreadyExistsException e) {
            System.out.println("Unluck bratan");;
        }
    }

    private void loadAuthorities() {
        Arrays.stream(AuthorityType.values()).forEach(authorityName -> {
            Optional<AuthorityEntity> authority = authorityRepository.findByAuthority(authorityName.toString());

            if (authority.isEmpty()) {
                authorityRepository.save(new AuthorityEntity(authorityName.toString()));
            }
        });
    }

    private void loadSuperAdminUser() throws UsernameAlreadyExistsException {
        AuthorityEntity adminRoleEntity;
        if (authorityRepository.findByAuthority("ADMIN").isEmpty()) {
            adminRoleEntity = authorityRepository.save(new AuthorityEntity("ADMIN"));
        } else {
            adminRoleEntity = authorityRepository.findByAuthority("ADMIN").get();
        }

        RegistrationResponseDTO registrationResponseDTO = authenticationService.registerUser(
                new RegistrationRequestDTO("admin", "admin"));

        UserEntity userEntity = userRepository.findByUsername(registrationResponseDTO.getUsername()).get();

        Set<AuthorityEntity> authorityEntitySet = userEntity.getAuthorities();
        authorityEntitySet.add(adminRoleEntity);

        userEntity.setAuthorities(authorityEntitySet);
        userRepository.save(userEntity);
    }
}

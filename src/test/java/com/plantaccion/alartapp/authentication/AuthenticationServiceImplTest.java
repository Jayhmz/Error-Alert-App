package com.plantaccion.alartapp.authentication;

import com.plantaccion.alartapp.authentication.dto.RegistrationDTO;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
import com.plantaccion.alartapp.authentication.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private AppUserRepository repository;
    @Mock
    private AppUserService service;
    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser() {
        RegistrationDTO registrationDTO = new RegistrationDTO("19623","James", "Damilare", "a@a.com",  "password");
        AppUser expectedUser = new AppUser("19623","James","Damilare",  "a@a.com", Roles.valueOf("ADMIN"), "password");
        when(encoder.encode("password")).thenReturn("password");
        when(repository.save(any(AppUser.class))).then(i -> i.getArgument(0));
    }
}
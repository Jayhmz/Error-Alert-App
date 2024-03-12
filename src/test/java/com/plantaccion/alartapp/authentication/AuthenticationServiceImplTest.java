package com.plantaccion.alartapp.authentication;

import com.plantaccion.alartapp.authentication.dto.RegistrationDTO;
import com.plantaccion.alartapp.authentication.service.AppUserService;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    }
}
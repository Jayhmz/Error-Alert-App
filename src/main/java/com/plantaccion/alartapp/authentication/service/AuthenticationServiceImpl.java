package com.plantaccion.alartapp.authentication.service;

import com.plantaccion.alartapp.authentication.dto.RegistrationDTO;
import com.plantaccion.alartapp.authentication.dto.SignInDTO;
import com.plantaccion.alartapp.authentication.jwt.JWTService;
import com.plantaccion.alartapp.authentication.response.RegistrationResponse;
import com.plantaccion.alartapp.common.enums.LoginProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.app.ICOProfileRepository;
import com.plantaccion.alartapp.common.repository.app.RCHProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AppUserService {

    private final PasswordEncoder encoder;
    private final AppUserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RCHProfileRepository rchProfileRepository;
    private final ICOProfileRepository icoProfileRepository;

    public AuthenticationServiceImpl(PasswordEncoder encoder, AppUserRepository repository,
                                     AuthenticationManager authenticationManager, JWTService jwtService, RCHProfileRepository rchProfileRepository, ICOProfileRepository icoProfileRepository) {
        this.encoder = encoder;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.rchProfileRepository = rchProfileRepository;
        this.icoProfileRepository = icoProfileRepository;
    }

    @Override
    @Transactional
    public RegistrationResponse createUser(RegistrationDTO regDTO) {
        AppUser user = new AppUser(regDTO.getStaffId(), regDTO.getFirstname(), regDTO.getLastname(),
                regDTO.getEmail(), Roles.ADMIN, encoder.encode(regDTO.getPassword()));
        user.setProvider(LoginProvider.BASIC);
                repository.save(user);
        var r = new RegistrationResponse(user.getStaffId(), user.getFirstname(),
                user.getLastname(), user.getEmail(), user.getRole());
        return r;
    }

    @Override
    public String authenticate(SignInDTO signInDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
        if (authenticate.isAuthenticated()){
            var appUser = (AppUser) authenticate.getPrincipal();
           return  tokenGenerator(signInDTO, appUser);
        }else{
            throw new NullPointerException("Incorrect username or password");
        }
    }

    private String tokenGenerator(SignInDTO signInDTO, AppUser appUser) {
        String token ;
        switch(appUser.getRole()){
            case RCH -> {
                String cluster = rchProfileRepository.findByStaff(appUser).getCluster().getName();
                token = jwtService.generateToken(signInDTO.getEmail(), appUser, cluster);
            }
            case ICO -> {
                String cluster = icoProfileRepository.findByStaffId(appUser.getStaffId()).getOnboardedBy().getCluster().getName();
               token =  jwtService.generateToken(signInDTO.getEmail(), appUser, cluster);
            }
            default -> {
                String cluster = "Head Office";
                token = jwtService.generateToken(signInDTO.getEmail(), appUser, cluster);
            }
        }
        return token;
    }
}

package com.plantaccion.alartapp.authentication.provider;

import com.plantaccion.alartapp.common.model.app.AppUser;
import com.plantaccion.alartapp.common.repository.app.AppUserRepository;
import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import com.plantaccion.alartapp.exception.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Value("${auth.entity.table}")
    private String tableName;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public UsernamePasswordAuthenticationProvider(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        var user = authenticationRepository.findByEmail(username)
                .orElseThrow(() -> new StaffNotFoundException("Unauthorized user"));

        if(user != null && encoder.matches(password, user.getPassword())){
            var appUser = repository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new StaffNotFoundException("Unauthorized user"));
            return new UsernamePasswordAuthenticationToken(appUser, password, getRoles(appUser));
        } else {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    private List<GrantedAuthority> getRoles(AppUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().name());
        authorities.add(role);
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

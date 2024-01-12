package com.plantaccion.alartapp.authentication.provider;

import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.repository.AppUserRepository;
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
        AppUser user;
        try {
             user = repository.findByEmail(username);
        } catch (NullPointerException e) {
            throw new UsernameNotFoundException("Incorrect username or password");
        }

        if (encoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, getRoles(user));
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

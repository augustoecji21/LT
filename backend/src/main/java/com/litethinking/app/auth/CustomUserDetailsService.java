package com.litethinking.app.auth;

import com.litethinking.app.domain.UserAccount;
import com.litethinking.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

        
        Collection<? extends GrantedAuthority> auths =
                u.getRoles().stream()
                 .map(r -> new SimpleGrantedAuthority(r.getName()))
                 .collect(Collectors.toList());

        return new User(
                u.getEmail(),                 // username
                u.getPassword(),              // password (BCrypt)
                u.isEnabled(),                
                true, true, true,
                auths
        );
    }
}

package com.clothesdelivery.web.security;

import com.clothesdelivery.web.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository _users;

    @Autowired
    public CustomUserDetailsService(IUserRepository users) {
        _users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = _users.findByEmail(username);

        if(user != null) {
            var grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
            return new User(user.getEmail(), user.getPassword(), Collections.singleton(grantedAuthority));
        }

       throw new UsernameNotFoundException("Username not found.");
    }
}

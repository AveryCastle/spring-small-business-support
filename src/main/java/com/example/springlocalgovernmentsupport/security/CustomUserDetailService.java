package com.example.springlocalgovernmentsupport.security;

import com.example.springlocalgovernmentsupport.domains.User;
import com.example.springlocalgovernmentsupport.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    public User save(String username, String password, List<String> roles) {
        String encodedPassword = passwordEncoder.encode(password);
        if (roles == null || roles.size() == 0) {
            roles = Arrays.asList("ROLE_USER");
        }
        User user = User.builder().username(username).password(encodedPassword).roles(roles).build();
        return userRepository.save(user);
    }
}

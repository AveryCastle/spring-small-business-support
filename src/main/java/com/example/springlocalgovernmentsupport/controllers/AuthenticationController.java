package com.example.springlocalgovernmentsupport.controllers;

import static org.springframework.http.ResponseEntity.ok;

import com.example.springlocalgovernmentsupport.domains.User;
import com.example.springlocalgovernmentsupport.dtos.AuthenticationRequest;
import com.example.springlocalgovernmentsupport.exceptions.DuplicatedUserException;
import com.example.springlocalgovernmentsupport.repositories.UserRepository;
import com.example.springlocalgovernmentsupport.security.CustomUserDetailService;
import com.example.springlocalgovernmentsupport.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new DuplicatedUserException(username);
        }

        userDetailsService.save(username, authenticationRequest.getPassword(), null);

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);

        return ok(model);
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword()));
        User user = (User) userDetailsService.loadUserByUsername(username);
        String token = jwtTokenProvider.createToken(username, user.getRoles());

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return ok(model);
    }
}

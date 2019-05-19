package com.example.springlocalgovernmentsupport.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.springlocalgovernmentsupport.domains.User;
import com.example.springlocalgovernmentsupport.dtos.AuthenticationRequest;
import com.example.springlocalgovernmentsupport.security.CustomUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

@SpringBootTest
class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(applicationContext).apply(springSecurity()).build();
    }

    @Test
    public void signUp() throws Exception {
        final String USERNAME = "user";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(USERNAME, "password");
        String jsonString = objectMapper.writeValueAsString(authenticationRequest);

        mockMvc.perform(post("/auth/signup").content(jsonString).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)));
    }

    @Test
    @WithMockUser(username = "spring")
    public void signIn() throws Exception {
        final String USERNAME = "spring";
        final String PASSWORD = "secret";
        User existedUser = User.builder().username(USERNAME).password(PASSWORD).roles(Arrays.asList("USER")).build();
        when(customUserDetailService.loadUserByUsername(USERNAME)).thenReturn(existedUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(USERNAME, PASSWORD);
        String jsonString = objectMapper.writeValueAsString(authenticationRequest);

        mockMvc.perform(post("/auth/signin").content(jsonString).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.token", notNullValue()));
    }
}

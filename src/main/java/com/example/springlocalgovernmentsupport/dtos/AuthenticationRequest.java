package com.example.springlocalgovernmentsupport.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Builder
    public AuthenticationRequest(@NotEmpty String username, @NotEmpty String password) {
        this.username = username;
        this.password = password;
    }
}

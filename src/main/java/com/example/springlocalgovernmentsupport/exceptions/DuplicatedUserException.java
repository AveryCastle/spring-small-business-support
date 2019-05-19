package com.example.springlocalgovernmentsupport.exceptions;

public class DuplicatedUserException extends RuntimeException {

    public DuplicatedUserException(String msg) {
        super(String.format("%s는 이미 존재하는 username 입니다.", msg));
    }
}

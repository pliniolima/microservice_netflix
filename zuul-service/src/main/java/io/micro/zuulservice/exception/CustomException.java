package io.micro.zuulservice.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomException extends UsernameNotFoundException {

    public CustomException(String msg) {
        super(msg);
    }
}

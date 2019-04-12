package io.micro.zuulservice.service;

import io.micro.zuulservice.bean.auth.User;

public interface LoginService {
    public String login(String username, String password);

    public User save(User user);

    public boolean logout(String token);

    public boolean isValidToken(String token);

    public String createToken(String token);
}

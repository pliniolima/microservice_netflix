package io.micro.zuulservice.serviceimpl;

import io.micro.zuulservice.bean.auth.JwtToken;
import io.micro.zuulservice.bean.auth.User;
import io.micro.zuulservice.exception.CustomException;
import io.micro.zuulservice.security.JwtTokenProvider;
import io.micro.zuulservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = restTemplate.getForObject("http://mongodb-service/db/user/find/" + username, User.class);

        if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new CustomException("Invalid username or password" + HttpStatus.UNAUTHORIZED);
        }

        JwtToken token = jwtTokenProvider.createToken(username,
                user.getRole().stream()
                        .map(role -> "ROLE_" + role.getRole())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));

        return token.getToken();
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = restTemplate.postForObject("http://mongodb-service/db/user/save", user, User.class);

        return newUser;
    }

    @Override
    public boolean logout(String token) {
        restTemplate.delete("http://mongodb-service/db/token/delete", token);

        return true;
    }

    @Override
    public boolean isValidToken(String token) {
        return jwtTokenProvider.validToken(token);
    }

    @Override
    public String createToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String> roles = jwtTokenProvider.getRoles(token);
        JwtToken newToken = jwtTokenProvider.createToken(username, roles);

        return newToken.getToken();
    }
}
package io.micro.zuulservice.security;

import io.micro.zuulservice.bean.auth.MongoUserDetails;
import io.micro.zuulservice.bean.auth.Role;
import io.micro.zuulservice.bean.auth.User;
import io.micro.zuulservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = restTemplate.getForObject("http://mongodb-service/db/user/find/" + email, User.class);

        if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new CustomException("Invalid username or password." + HttpStatus.UNAUTHORIZED);
        }

        String[] authorities = new String[user.getRole().size()];
        int count = 0;

        for (Role role : user.getRole()) {
            authorities[count] = "ROLE_" + role.getRole();
            count++;
        };

        MongoUserDetails userDetails = new MongoUserDetails(user.getEmail(), user.getPassword(), user.getActive(), user.isLocked(), user.isExpired(), user.isEnabled(), authorities);

        return userDetails;
    }
}

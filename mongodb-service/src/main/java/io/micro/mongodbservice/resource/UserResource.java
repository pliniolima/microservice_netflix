package io.micro.mongodbservice.resource;

import io.micro.mongodbservice.document.User;
import io.micro.mongodbservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/db/user")
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/find/{email}")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @PostMapping("/save")
    public String addUser(@RequestBody User user) {
        User saved = userRepository.save(user);

        return saved != null ? "SAVED" : "ERROR";
    }
}

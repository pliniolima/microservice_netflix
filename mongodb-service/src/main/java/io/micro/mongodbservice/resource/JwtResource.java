package io.micro.mongodbservice.resource;

import io.micro.mongodbservice.document.JwtToken;
import io.micro.mongodbservice.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/db/token")
public class JwtResource {

    @Autowired
    JwtTokenRepository jwtTokenRepository;

    @GetMapping("/find")
    public Optional<JwtToken> getToken(String token) {
        return jwtTokenRepository.findById(token);
    }

    @PostMapping("/save")
    public String save(@RequestBody JwtToken token) {
        JwtToken saved = jwtTokenRepository.save(token);

        return saved != null ? "SAVED" : "ERROR";
    }

    @DeleteMapping("/delete")
    public String delete(String token) {
        jwtTokenRepository.deleteById(token);

        return "TOKEN DELETED";
    }
}

package io.micro.zuulservice.controller;

import io.micro.zuulservice.bean.auth.AuthResponse;
import io.micro.zuulservice.bean.auth.LoginRequest;
import io.micro.zuulservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();

        List<String> headersList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();

        headersList.add("content-type");
        headersList.add("Accept");
        headersList.add("X-Requested-With");
        headersList.add("Authorization");
        headers.setAccessControlAllowHeaders(headersList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);

        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();

        if (loginService.logout(token)) {
            headers.remove("Authorization");
            return new ResponseEntity<AuthResponse>(new AuthResponse("Logged out"), headers, HttpStatus.CREATED);
        }

        return new ResponseEntity<AuthResponse>(new AuthResponse("Logged out"), headers, HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/valid/token")
    @ResponseBody
    public boolean isValidToken(@RequestHeader(value = "Authorization") String token) {
        return true;
    }

    @CrossOrigin("*")
    @PostMapping("/signin/token")
    @ResponseBody
    public ResponseEntity<AuthResponse> createToken(@RequestHeader(value = "Authorization") String token) {
        String newToken = loginService.createToken(token);

        HttpHeaders headers = new HttpHeaders();

        List<String> headersList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();

        headersList.add("content-type");
        headersList.add("Accept");
        headersList.add("X-Requested-With");
        headersList.add("Authorization");
        headers.setAccessControlAllowHeaders(headersList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", newToken);

        return new ResponseEntity<AuthResponse>(new AuthResponse(newToken), headers, HttpStatus.CREATED);

    }
}

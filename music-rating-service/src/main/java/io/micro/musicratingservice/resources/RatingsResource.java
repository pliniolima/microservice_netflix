package io.micro.musicratingservice.resources;

import io.micro.musicratingservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") Integer userId) {
        return restTemplate.getForObject("http://mongodb-service/db/rating/find/user/" + userId, UserRating.class);
    }
}

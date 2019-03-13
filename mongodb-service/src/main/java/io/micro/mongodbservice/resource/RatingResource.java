package io.micro.mongodbservice.resource;

import io.micro.mongodbservice.document.Rating;
import io.micro.mongodbservice.document.UserRating;
import io.micro.mongodbservice.repository.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db/rating")
public class RatingResource
{
    @Autowired
    private UserRatingRepository userRatingRepository;

    @GetMapping("/all")
    public List<UserRating> getAll() {
        return userRatingRepository.findAll();
    }

    @GetMapping("/find/user/{userId}")
    public Optional<UserRating> getUserRating(@PathVariable("userId") Integer userId) {
        return userRatingRepository.findById(userId);
    }

    @PostMapping("/save")
    public void save(@RequestBody UserRating userRating) {
        userRatingRepository.save(userRating);
    }

}

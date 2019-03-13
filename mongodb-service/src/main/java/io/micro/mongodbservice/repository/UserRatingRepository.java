package io.micro.mongodbservice.repository;

import io.micro.mongodbservice.document.UserRating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRatingRepository extends MongoRepository<UserRating, Integer> {
}

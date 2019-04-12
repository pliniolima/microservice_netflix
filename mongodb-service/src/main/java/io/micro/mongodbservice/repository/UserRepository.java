package io.micro.mongodbservice.repository;

import io.micro.mongodbservice.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value="{email : ?0}")
    public User findByEmail(String email);
}

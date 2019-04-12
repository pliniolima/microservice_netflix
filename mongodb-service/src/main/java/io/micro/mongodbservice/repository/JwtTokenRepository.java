package io.micro.mongodbservice.repository;

import io.micro.mongodbservice.document.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JwtTokenRepository extends MongoRepository<JwtToken, String> {
}

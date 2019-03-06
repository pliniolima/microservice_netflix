package io.micro.mongodbservice.repository;

import io.micro.mongodbservice.document.MusicInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicInfoRepository extends MongoRepository<MusicInfo, Integer> {
}

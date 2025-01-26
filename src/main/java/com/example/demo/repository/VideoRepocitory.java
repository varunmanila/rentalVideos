package com.example.demo.repository;

import com.example.demo.model.Videos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepocitory extends MongoRepository<Videos,String> {
}

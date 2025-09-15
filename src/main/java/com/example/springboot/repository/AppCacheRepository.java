package com.example.springboot.repository;

import com.example.springboot.models.AppCache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppCacheRepository extends MongoRepository<AppCache, ObjectId> {

}

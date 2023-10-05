package com.test.docs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.docs.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}

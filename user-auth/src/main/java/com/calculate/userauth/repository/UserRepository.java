package com.calculate.userauth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.calculate.userauth.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username:'?0'}")
    User findUserByUsername(String username);

    @Query("{username:'?0', password:'?1'}")
    User findUserByUsernameAndPassword(String username, String password);
}

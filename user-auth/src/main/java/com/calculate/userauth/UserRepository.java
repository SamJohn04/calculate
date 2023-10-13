package com.calculate.userauth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username:'?0'}")
    User findUserByUsername(String username);

    @Query("{username:'?0', password:'?1'}")
    User findUserByUsernameAndPassword(String username, String password);
}

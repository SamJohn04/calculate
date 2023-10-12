package com.calculate.storeResults;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LogRepository extends MongoRepository<Log, String> {
    
    @Query(value="{username:'?0'}", fields="{expression : 1, result : 1}")
    List<Log> findLogsByUsername(String username);
    
    @Query(value="{username:'?0'}", fields="{expression : 1, result : 1}")
    List<Log> findAll(String category);
    
    public long count();

}

package com.calculate.storeResults;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class StoreResultAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreResultAppApplication.class, args);
	}

}

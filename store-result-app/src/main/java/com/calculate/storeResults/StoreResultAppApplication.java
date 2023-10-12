package com.calculate.storeResults;

import java.util.LinkedHashMap;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@EnableMongoRepositories
@SpringBootApplication
@RestController
public class StoreResultAppApplication {

	@Autowired
	private LogRepository logRepo;

	public static void main(String[] args) {
		SpringApplication.run(StoreResultAppApplication.class, args);
	}

	public Log parseLog(String reqBodyString) throws ParseException {
		JSONParser jsonParser = new JSONParser(reqBodyString);
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> log = (LinkedHashMap<String, String>) jsonParser.parse();

		return new Log(log.get("username"), log.get("expression"), Double.parseDouble(log.get("result")));
	}

	public void storeResult(Log log) {
		System.out.println(log);
		logRepo.save(log);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/log")
	public void storeResults(@RequestBody String reqBody) throws ParseException {
		Log log = parseLog(reqBody);
		storeResult(log);
	}
}

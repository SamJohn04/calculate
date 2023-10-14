package com.calculate.storeResults;

import java.util.LinkedHashMap;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    @Autowired
	private LogRepository logRepo;

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

    @GetMapping(path="/log")
    public ResponseEntity<Iterable<Log>> getLogs() {
        return ResponseEntity.ok(logRepo.findAll());
    }

    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/log")
	public ResponseEntity<String> storeResults(@RequestBody String reqBody) {
		try {
            Log log = parseLog(reqBody);
		    storeResult(log);
            return ResponseEntity.ok("Log Stored Successfully");
        } catch(ParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Parsing failed");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error Storing Log");
        }
	}
}

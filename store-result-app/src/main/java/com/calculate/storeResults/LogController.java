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

import com.calculate.storeResults.traceLog.TraceLog;
import com.calculate.storeResults.traceLog.TraceLogAspect;

@RestController
public class LogController {
    @Autowired
	private LogRepository logRepo;

    private final Expression expression;

    @Autowired
    public LogController(TraceLogAspect traceLogAspect, Expression expression) {
        this.expression = expression;
    }

    @TraceLog
    public Log parseLog(String reqBodyString) throws ParseException {
		JSONParser jsonParser = new JSONParser(reqBodyString);
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> log = (LinkedHashMap<String, String>) jsonParser.parse();

        expression.setExpression(log.get("expression"));
        expression.extractFromExpression();

		return new Log(log.get("username"), expression, Double.parseDouble(log.get("result")));
	}

    @TraceLog
    public void storeResult(Log log) {
		System.out.println(log);
		logRepo.save(log);
	}

    @TraceLog
    @GetMapping(path="/log")
    public ResponseEntity<Iterable<Log>> getLogs() {
        return ResponseEntity.ok(logRepo.findAll());
    }

    @TraceLog
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

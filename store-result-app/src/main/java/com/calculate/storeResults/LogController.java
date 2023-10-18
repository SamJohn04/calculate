package com.calculate.storeResults;

import java.util.ArrayList;
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

    private final TraceLogAspect traceLogAspect;

    private final Expression expression;

    @Autowired
    public LogController(TraceLogAspect traceLogAspect, Expression expression) {
        this.traceLogAspect = traceLogAspect;
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
	public ResponseEntity<Result> storeResults(@RequestBody String reqBody) {
		try {
            Log log = parseLog(reqBody);
		    storeResult(log);

            ArrayList<TraceLogItem> traceLog = traceLogAspect.getTraceLog();
		    for(TraceLogItem traceLogItem: traceLog) {
			    System.out.println(traceLogItem.getMethod());
			    for(String arg: traceLogItem.getArgs()) {
				    System.out.println(arg);
			    }
		    }

            return ResponseEntity.ok(new Result("Log Stored Successfully", traceLog));
        } catch(ParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new Result("Parsing failed", traceLogAspect.getTraceLog()));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new Result("Error Storing Log", traceLogAspect.getTraceLog()));
        }
	}
}

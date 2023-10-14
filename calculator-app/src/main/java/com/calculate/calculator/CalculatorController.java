package com.calculate.calculator;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    @GetMapping(path="/calculate")
	public Result calculate() {
		return new Result();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/calculate")
	public ResponseEntity<Result> calculate(@RequestBody String expression) throws ParseException {
		Expression expr = new Expression(expression);
		expr.extractFromExpression();
		double result = expr.calculate();
		return ResponseEntity.ok(new Result(result));
	}
}

package com.calculate.calculator;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CalculatorAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorAppApplication.class, args);
	}

	@GetMapping(path="/calculate")
	public Result calculate() {
		return new Result();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/calculate")
	public Result calculate(@RequestBody String expression) throws ParseException {
		Expression expr = new Expression(expression);
		expr.extractFromExpression();
		double result = expr.calculate();
		return new Result(result);
	}
}

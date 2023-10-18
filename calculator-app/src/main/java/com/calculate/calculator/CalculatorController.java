package com.calculate.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculate.calculator.traceLog.TraceLog;
import com.calculate.calculator.traceLog.TraceLogAspect;

@RestController
public class CalculatorController {

	private final Expression expression;

	@Autowired
	public CalculatorController(TraceLogAspect traceLogAspect, Expression expression) {
		this.expression = expression;
	}

    @GetMapping(path="/calculate")
	public Result calculateExpressionResult() {
		return new Result();
	}

	@TraceLog
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/calculate")
	public ResponseEntity<Result> calculateExpressionResult(@RequestBody String expression) {
		double result = 0;
		try {
			this.expression.setExpression(expression);
			this.expression.extractFromExpression();
			result = this.expression.calculate();
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new Result(e.getMessage()));
		}
		return ResponseEntity.ok(new Result(result));
	}
}

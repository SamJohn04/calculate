package com.calculate.calculator;

import java.util.ArrayList;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculate.calculator.traceLog.TraceLog;
import com.calculate.calculator.traceLog.TraceLogAspect;
import com.calculate.calculator.traceLog.TraceLogItem;

@RestController
public class CalculatorController {
	private final TraceLogAspect traceLogAspect;

	private final Expression expression;

	@Autowired
	public CalculatorController(TraceLogAspect traceLogAspect, Expression expression) {
		this.traceLogAspect = traceLogAspect;
		this.expression = expression;
	}

    @GetMapping(path="/calculate")
	public Result calculateExpressionResult() {
		return new Result();
	}

	@TraceLog
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path="/calculate")
	public ResponseEntity<Result> calculateExpressionResult(@RequestBody String expression) throws ParseException {
		this.expression.setExpression(expression);
		this.expression.extractFromExpression();
		double result = this.expression.calculate();
		
		ArrayList<TraceLogItem> traceLog = traceLogAspect.getTraceLog();
		for(TraceLogItem traceLogItem: traceLog) {
			System.out.println(traceLogItem.getMethod());
			for(String arg: traceLogItem.getArgs()) {
				System.out.println(arg);
			}
		}

		return ResponseEntity.ok(new Result(result, traceLog));
	}
}

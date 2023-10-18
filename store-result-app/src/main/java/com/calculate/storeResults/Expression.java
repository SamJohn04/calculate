package com.calculate.storeResults;

import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import com.calculate.storeResults.traceLog.TraceLog;

@Service
public class Expression {
    String expression;
    double firstOperand, secondOperand;
    char operator;
    
    public Expression() {
        this.expression = "";
        this.firstOperand = 0;
        this.secondOperand = 0;
        this.operator = ' ';
    }

    public Expression(String expression) {
        this.expression = expression;
        this.firstOperand = 0;
        this.secondOperand = 0;
        this.operator = ' ';
    }

    @TraceLog
    public String getExpression() {
        return this.expression;
    }

    @TraceLog
    public double getFirstOperand() {
        return this.firstOperand;
    }

    @TraceLog
    public double getSecondOperand() {
        return this.secondOperand;
    }

    @TraceLog
    public char getOperator() {
        return this.operator;
    }

    @TraceLog
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @TraceLog
    public void extractFromExpression() {
        StringTokenizer tokenizer = new StringTokenizer(this.expression, "+-*/%^", true);

        this.firstOperand = Double.parseDouble(tokenizer.nextToken());
        this.operator = tokenizer.nextToken().charAt(0);
        this.secondOperand = Double.parseDouble(tokenizer.nextToken());
    }
}

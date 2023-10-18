package com.calculate.calculator;

import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

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
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @TraceLog
    public String toString() {
        return "{ expression: " + this.expression + "; firstOperand: " + this.firstOperand + "; secondOperand: " + this.secondOperand + "; operator: " + this.operator + " }";
    }

    @TraceLog
    public void extractFromExpression() {
        try {
        StringTokenizer tokenizer = new StringTokenizer(this.expression, "+-*/%^", true);

        this.firstOperand = Double.parseDouble(tokenizer.nextToken());
        this.operator = tokenizer.nextToken().charAt(0);
        this.secondOperand = Double.parseDouble(tokenizer.nextToken());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @TraceLog
    public double calculate(Expression this) {
        switch(this.operator) {
            case '+': return this.firstOperand + this.secondOperand;
            case '-': return this.firstOperand - this.secondOperand;
            case '*': return this.firstOperand * this.secondOperand;
            case '/': return this.firstOperand / this.secondOperand;
            case '%': return this.firstOperand % this.secondOperand;
            case '^': return Math.pow(this.firstOperand, this.secondOperand);
            default: return -1;
        }
    }
}

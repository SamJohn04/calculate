package com.calculate.storeResults;

import java.util.StringTokenizer;

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

    public String getExpression() {
        return this.expression;
    }

    public double getFirstOperand() {
        return this.firstOperand;
    }

    public double getSecondOperand() {
        return this.secondOperand;
    }

    public void extractFromExpression() {
        StringTokenizer tokenizer = new StringTokenizer(this.expression, "+-*/%^", true);

        this.firstOperand = Double.parseDouble(tokenizer.nextToken());
        this.operator = tokenizer.nextToken().charAt(0);
        this.secondOperand = Double.parseDouble(tokenizer.nextToken());
    }

    public double calculate() {
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

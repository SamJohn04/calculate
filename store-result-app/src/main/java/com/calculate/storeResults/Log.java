package com.calculate.storeResults;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("calculations")
public class Log {
    private double result;
    private String username;
    private Expression expression;

    public Log() {
        this.result = 0;
        this.username = "";
        this.expression = null;
    }

    public Log(String username, String expression, double result) {
        this.result = result;
        this.username = username;
        this.expression = new Expression(expression);
        this.expression.extractFromExpression();
    }

    public Log(String username, Expression expression, double result) {
        this.result = result;
        this.username = username;
        this.expression = expression;
    }

    public double getResult() {
        return this.result;
    }

    public String getUsername() {
        return this.username;
    }

    public Expression getExpression() {
        return this.expression;
    }
}

package com.calculate.calculator;

public class Result {
    private final double result;
    private final String message;
    public Result() {
        this.result = 0;
        this.message = "";
    }

    public Result(double result) {
        this.result = result;
        this.message = "";
    }

    public Result(String message) {
        this.result = 0;
        this.message = message;
    }

    public Result(double result, String message) {
        this.result = result;
        this.message = message;
    }

    public double getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}

package com.calculate.storeResults;

public class TraceLogItem {
    private final String method;
    private final String[] args;
    public TraceLogItem(String method, String[] args) {
        this.method = method;
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public String[] getArgs() {
        return args;
    }
}

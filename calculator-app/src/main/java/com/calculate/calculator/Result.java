package com.calculate.calculator;

import java.util.ArrayList;

public class Result {
    private final double result;
    private final TraceLogItem[] traceLog;
    public Result() {
        this.result = 0;
        this.traceLog = new TraceLogItem[0];
    }

    public Result(double result) {
        this.result = result;
        this.traceLog = new TraceLogItem[0];
    }

    public Result(double result, ArrayList<TraceLogItem> traceLog) {
        this.result = result;
        this.traceLog = new TraceLogItem[traceLog.size()];
        for(int i = 0; i < traceLog.size(); i++) {
            this.traceLog[i] = traceLog.get(i);
        }
    }

    public double getResult() {
        return result;
    }

    public TraceLogItem[] getTraceLog() {
        return traceLog;
    }
}

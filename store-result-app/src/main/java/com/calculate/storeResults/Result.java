package com.calculate.storeResults;

import java.util.ArrayList;

import com.calculate.storeResults.traceLog.TraceLogItem;

public class Result {
    private final String message;
    private final TraceLogItem[] traceLog;

    public Result() {
        this.message = "";
        this.traceLog = new TraceLogItem[0];
    }

    public Result(String message) {
        this.message = message;
        this.traceLog = new TraceLogItem[0];
    }

    public Result(String message, ArrayList<TraceLogItem> traceLog) {
        this.message = message;
        this.traceLog = new TraceLogItem[traceLog.size()];
        for(int i = 0; i < traceLog.size(); i++) {
            this.traceLog[i] = traceLog.get(i);
        }
    }

    public String getMessage() {
        return message;
    }

    public TraceLogItem[] getTraceLog() {
        return traceLog;
    }
}

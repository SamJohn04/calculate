package com.calculate.storeResults;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceLogAspect {

    private ArrayList<TraceLogItem> traceLog = new ArrayList<TraceLogItem>();

    @Pointcut("@annotation(TraceLog)")
    public void traceLogPointcut() {
    }

    // @Before("execution(* com.calculate.calculator.*.*(..))")
    // public void beforeMethodExecution(JoinPoint joinPoint) {
    //     // System.out.println(traceLog);
    //     Object args[] = joinPoint.getArgs();
    //     System.out.print("Method called: " + joinPoint.getSignature().getName() + "(");
    //     for(int i = 0; i < args.length; i++) {
    //         System.out.print(args[i]);
    //         if(i != args.length - 1) {
    //             System.out.print(", ");
    //         }
    //     }
    //     System.out.println(")");
    // }

    @Before("traceLogPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        Object args[] = joinPoint.getArgs();
        String traceLogValues[] = new String[args.length];
        System.out.print("Method called: " + joinPoint.getSignature().getName() + "(");
        for(int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if(i != args.length - 1) {
                System.out.print(", ");
            }
            traceLogValues[i ] = args[i].toString();
        }
        System.out.println(")");
        
        traceLog.add(new TraceLogItem(joinPoint.getSignature().getName(), traceLogValues));
    }

    public ArrayList<TraceLogItem> getTraceLog() {
        return traceLog;
    }
}

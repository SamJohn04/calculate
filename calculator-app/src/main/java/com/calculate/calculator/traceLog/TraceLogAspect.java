package com.calculate.calculator.traceLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceLogAspect.class);

    @Pointcut("@annotation(TraceLog)")
    public void traceLogPointcut() {
    }

    @Before("traceLogPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        Object args[] = joinPoint.getArgs();
        String methodArguments = "";
        for(int i = 0; i < args.length; i++) {
            methodArguments += (args[i].getClass().getSimpleName().equals("String") ? "\"" + args[i].toString() + "\"" : args.toString()) + (i < args.length - 1 ? ", " : "");
        }
        
        logger.info("\n\u001B[42;1m[METHOD]\u001B[0m: {} \033[60G \u001B[42;1m[ARGS]\u001B[0m: \u001B[34;1m{}\u001B[0m", joinPoint.getSignature().getName(), methodArguments);
    }
}

package com.calculate.userauth.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calculate.userauth.model.User;
import com.calculate.userauth.service.AuthService.UserAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;

@Aspect
@Component
public class AuthLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuthLogAspect.class);
    @Autowired
    private ObjectMapper mapper;

    @Pointcut("execution(* com.calculate.userauth.controller.AuthController.*(..))")
    public void inControllerMethod() {
    }

    @Pointcut("execution(* com.calculate.userauth.service.AuthService.*(..))")
    public void inServiceMethod() {
    }

    @Pointcut("execution(* com.calculate.userauth.repository.UserRepository.*(..))")
    public void inRepositoryMethod() {
    }

    @Before("inControllerMethod()")
    public void beforeControllerRequest(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping annotation = signature.getMethod().getAnnotation(RequestMapping.class);
        Object args[] = joinPoint.getArgs();
        StringBuilder argsString = new StringBuilder("(");
        for (int i = 0; i < args.length; i++) {
            argsString.append(args[i]);
            if (i != args.length - 1) {
                argsString.append(", ");
            }
        }
        argsString.append(")");
        logger.info(
                "[\u001B[34;1m==>\u001B[0m] \u001B[0;1m[HTTP_REQUEST]\u001B[0m path: \u001B[32;1m{}\u001B[0m method: \u001B[33;1m{}\u001B[0m arguments: \u001B[34m{}\u001B[0m",
                annotation.path(),
                annotation.method(),
                argsString.toString());
    }

    @AfterReturning(pointcut = "inControllerMethod()", returning = "response")
    public void afterControllerRequest(JoinPoint joinPoint, ResponseEntity<?> response) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping annotation = signature.getMethod().getAnnotation(RequestMapping.class);
        try {
            logger.info(
                    "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[HTTP_REQUEST]\u001B[0m path: \u001B[32;1m{}\u001B[0m method: \u001B[33;1m{}\u001B[0m response: \u001B[34m{}\u001B[0m",
                    annotation.path(),
                    annotation.method(),
                    mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            logger.error(
                    "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[HTTP_REQUEST]\u001B[0m \u001B[31;1m[ERROR]\u001B[0m PARSING ARGUMENTS");
        }
    }

    @Before("inServiceMethod()")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object args[] = joinPoint.getArgs();
        Map<String, Object> argumentsMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            String variableName = parameterNames[i];
            Object value = args[i];
            argumentsMap.put(variableName, value);
        }
        try {
            logger.info(
                    "[\u001B[34;1m==>\u001B[0m] \u001B[0;1m[SERVICE_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m arguments: \u001B[34m{}\u001B[0m",
                    signature.getName(),
                    mapper.writeValueAsString(argumentsMap));
        } catch (JsonProcessingException e) {
            logger.error(
                    "[\u001B[34;1m==>\u001B[0m] \u001B[0;1m[SERVICE_METHOD]\u001B[0m \u001B[31;1m[ERROR]\u001B[0m PARSING ARGUMENTS");
        }
    }

    @AfterReturning(pointcut = "inServiceMethod()", returning = "response")
    public void afterServiceSuccessMethod(JoinPoint joinPoint, User response) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info(
                "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[SERVICE_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m response: \u001B[34m{}\u001B[0m",
                signature.getName(),
                response.toString());
    }

    @AfterThrowing(pointcut = "inServiceMethod()", throwing = "exception")
    public void afterServiceFailMethod(JoinPoint joinPoint, UserAuthException exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        logger.error(
                "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[SERVICE_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m exception: \u001B[31;1m{}\u001B[0m",
                signature.getName(),
                exception.getMessage());
    }

    @Before("inRepositoryMethod()")
    public void beforeRepositoryMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object args[] = joinPoint.getArgs();
        Map<String, Object> argumentsMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            String variableName = parameterNames[i];
            Object value = args[i];
            argumentsMap.put(variableName, value);
        }
        Boolean isQuery = (signature.getMethod().getAnnotation(Query.class) != null);
        try {
            logger.info(
                    "[\u001B[34;1m==>\u001B[0m] \u001B[0;1m[REPOSITORY_METHOD]\u001B[0m"
                            + (isQuery ? " \u001B[33;1m[QUERY]\u001B[0m " : " ")
                            + "fn: \u001B[32;1m{}()\u001B[0m arguments: \u001B[34m{}\u001B[0m",
                    signature.getName(),
                    mapper.writeValueAsString(argumentsMap));
        } catch (JsonProcessingException e) {
            logger.error(
                    "[\u001B[34;1m==>\u001B[0m] \u001B[0;1m[REPOSITORY_METHOD]\u001B[0m \u001B[31;1m[ERROR]\u001B[0m PARSING ARGUMENTS");
        }
    }

    @AfterReturning(pointcut = "inRepositoryMethod()", returning = "response")
    public void afterRepositorySuccessMethod(JoinPoint joinPoint, Object response) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        User result = (User) response;

        if (result == null) {
            logger.error(
                    "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[REPOSITORY_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m response: \u001B[31;1mNO_RESPONSE\u001B[0m",
                    signature.getName());
        } else {
            logger.info(
                    "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[REPOSITORY_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m response: \u001B[34m{}\u001B[0m",
                    signature.getName(),
                    result.toString());
        }
    }

    @AfterThrowing(pointcut = "inRepositoryMethod()", throwing = "exception")
    public void afterRepositoryFailMethod(JoinPoint joinPoint, MongoException exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.error(
                "[\u001B[34;1m<==\u001B[0m] \u001B[0;1m[REPOSITORY_METHOD]\u001B[0m fn: \u001B[32;1m{}()\u001B[0m exception: \u001B[31;1m{}\u001B[0m",
                signature.getName(),
                exception.getMessage());
    }
}

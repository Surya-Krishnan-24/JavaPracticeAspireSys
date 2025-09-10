package com.example.UserService.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Around("execution(* com.example.UserService.Controller..*(..)) || " +
            "execution(* com.example.UserService.Service..*(..)) || " +
            "execution(* com.example.UserService.DOA..*(..))")
    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Object[] args = joinPoint.getArgs();

        log.info("\nClass called    : {}\nMethod called   : {}()\nArgs given      : {}\n",
                className, methodName, Arrays.toString(args));

        Object result;
        try {
            result = joinPoint.proceed();
        }
        catch (Throwable ex)
        {
            log.error("Exception in {}.{}(): {}", className, methodName, ex.getMessage(), ex);
            throw ex;
        }

        long timeTaken = System.currentTimeMillis() - start;

        log.info("Time taken      : {}.{}() in {} ms\n", className, methodName, timeTaken);

        return result;
    }
}

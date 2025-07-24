package com.example.SpringAOP.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logBefore(JoinPoint joinPoint){
        logger.info("[Before] Method called: "+joinPoint.getSignature().getName());
    }

    @After("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterFinally(JoinPoint joinPoint){
        logger.info("[After finally] Method called: "+joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterReturning(JoinPoint joinPoint){
        logger.info("[After Returning] Method called: "+joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterThrow(JoinPoint joinPoint){
        logger.info("[After Throwing] Method called: "+joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis();

        logger.info("Time runned: "+proceedingJoinPoint.getSignature().getName()+" "+(end - start)+" ms");
    }





}

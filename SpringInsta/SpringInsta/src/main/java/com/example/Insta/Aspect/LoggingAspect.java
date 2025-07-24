package com.example.Insta.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.Insta.Controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(* com.example.Insta.Service.*.*(..))")
    public void serviceLog(){}

    @Pointcut("execution(* com.example.Insta.Repo.*.*(..))")
    public void repoLog(){}

    @Around("controllerLog() || serviceLog() || repoLog()")
    public Object logExecuter(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Before running the Method: "+joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("Time Taken: "+(end-start)+" MilliSeconds for the Method: "+joinPoint.getSignature().getName());

        return result;
    }



}

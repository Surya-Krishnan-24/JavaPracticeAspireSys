package com.example.SpringEcom.AOP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("execution(* com.example.SpringEcom.Controller.ProductController.*(..))")
    public Object monitorTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object obj = proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis();

        LOGGER.info("Time taken : "+( end - start )+" Milliseconds by "+proceedingJoinPoint.getSignature().getName()+" Method");

        return obj;

    }
}

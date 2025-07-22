package com.example.SpringEcom.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {


    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // return type class name.method name(args .. )

    @Before("execution(* com.example.SpringEcom.Controller.ProductController.*(..))")
    public void logMethod(JoinPoint joinPoint){
        LOGGER.info("Before Method called "+ joinPoint.getSignature().getName());
    }


    @After("execution(* com.example.SpringEcom.Controller.ProductController.*(..))")
    public void logMethodFinally(JoinPoint joinPoint){
        LOGGER.info("Method Executed or Error Finally "+ joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* com.example.SpringEcom.Controller.ProductController.*(..))")
    public void logMethodError(JoinPoint joinPoint){
        LOGGER.info("Method throwing exception "+ joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.example.SpringEcom.Controller.ProductController.*(..))")
    public void logMethodReturn(JoinPoint joinPoint){
        LOGGER.info("Method excecuted successfully "+ joinPoint.getSignature().getName());
    }
}

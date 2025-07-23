package com.example.SpringAOP.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("[Before] Method called: "+joinPoint.getSignature().getName());
    }

    @After("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterFinally(JoinPoint joinPoint){
        System.out.println("[After finally] Method called: "+joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterReturning(JoinPoint joinPoint){
        System.out.println("[After Returning] Method called: "+joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAfterThrow(JoinPoint joinPoint){
        System.out.println("[After Throwing] Method called: "+joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.SpringAOP.Service.*.*(..))")
    public void logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("Time runned: "+proceedingJoinPoint.getSignature().getName()+" "+(end - start)+" ms");
    }





}

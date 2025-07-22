package com.example.SpringEcom.AOP;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* com.example.SpringEcom.Controller.ProductController.getProduct(..)) && args(id)")
    public Object validateAndUpdate(ProceedingJoinPoint proceedingJoinPoint,int id) throws Throwable {
        if(id<0){
            LOGGER.info("Post id is negative, "+ id+" changing to positive");
            id = -id;
            LOGGER.info("Post id is updated to positve "+id);
        }

        Object obj = proceedingJoinPoint.proceed(new Object[]{id});
        return obj;
    }
}

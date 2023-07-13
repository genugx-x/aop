package com.genug.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;

@Slf4j
@Aspect
public class AspectV6 {

    /* @Around("com.genug.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[Transaction start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[Transaction commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[Transaction rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[Resource release] {}", joinPoint.getSignature());
        }
    }*/

    @Before("com.genug.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[Before] {}", joinPoint.getSignature());
        // joinPoint.proceed(); 자동으로 처리
    }

    @AfterReturning(value = "com.genug.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[Return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.genug.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrow(JoinPoint joinPoint, Exception ex) {
        log.info("[Exception] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    @After("com.genug.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[After] {}", joinPoint.getSignature());
    }

}

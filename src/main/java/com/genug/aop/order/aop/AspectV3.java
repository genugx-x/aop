package com.genug.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* com.genug.aop.order..*(..))")
    private void allOrder(){} // pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[Transaction start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[Transaction commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[Transaction rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[Resource release] {}", joinPoint.getSignature());
        }
    }

}

package com.genug.aop.pointcut;

import com.genug.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ExecutionTest {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // public java.lang.String com.genug.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        pointcut.setExpression("execution(public String com.genug.aop.member.MemberServiceImpl.hello(String))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatchStart1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatchStart2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* false(..))");
        assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatch1() {
        pointcut.setExpression("execution(* com.genug.aop.member.MemberServiceImpl.hello(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatch2() {
        pointcut.setExpression("execution(* com.genug.aop.member.*.*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatch3() {
        pointcut.setExpression("execution(* com.genug.aop.member.*.*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatch4() {
        pointcut.setExpression("execution(* com.genug.aop..*.*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* com.genug.aop.member.MemberService.*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.genug.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertTrue(pointcut.matches(internalMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.genug.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertFalse(pointcut.matches(internalMethod, MemberServiceImpl.class));
    }

    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("정확히 하나의 파라미터 허용, 모든 타입 허용")
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))"); // .. : 0~무제한의 모든 타입 허용
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }
}

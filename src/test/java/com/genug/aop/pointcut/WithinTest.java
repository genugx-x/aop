package com.genug.aop.pointcut;

import com.genug.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithinTest {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(com.genug.aop.member.MemberServiceImpl)");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(com.genug.aop.member.*Service*)");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinPackage() {
        pointcut.setExpression("within(com.genug.aop..*)");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.genug.aop..*)");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.genug.aop.member.MemberService)");
        assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("execution은 타입 기반, 인터페이스를 선정 가능")
    void executionSuperTypeTrue() {
        pointcut.setExpression("execution(* com.genug.aop.member.MemberService.*(..))");
        assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

}

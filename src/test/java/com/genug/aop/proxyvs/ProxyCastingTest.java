package com.genug.aop.proxyvs;

import com.genug.aop.member.MemberService;
import com.genug.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시는 Impl에 대해 알지 못하기 때문에 캐스팅시 ClassCastException이 발생한다.
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMEmberServiceImpl= (MemberServiceImpl) proxyFactory.getProxy();
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 동적 프록시

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        MemberServiceImpl memberServiceImplProxy = (MemberServiceImpl) proxyFactory.getProxy();
    }
}

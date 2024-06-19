package ru.study.base.tgjavabot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Pointcut("execution(public * ru.study.base.tgjavabot.controller.RegistrationController.registration(..))")
    public void registration() {}

    @Around("registration()")
    public Object aroundRegistration(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Execution time: with authorities {} method {} executed in {} ms",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities(),
                joinPoint.getSignature().getName(),
                endTime - startTime
        );
        return result;
    }

    @Pointcut("execution(public * ru.study.base.tgjavabot.controller.RolesManageController.getRole(..))")
    public void getRole() {}

    @Around("getRole()")
    public Object aroundGetJokes(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Execution time: with authorities {} method {} executed in {} ms",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities(),
                joinPoint.getSignature().getName(),
                endTime - startTime
        );
        return result;
    }
}

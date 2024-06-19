package ru.study.base.tgjavabot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LogAspect{

    @Pointcut("execution(public * ru.study.base.tgjavabot..*(..))")
    public void callController(){ }

    @Before("callController()")
    public void logController(JoinPoint joinPoint){
        List<String> args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).toList();
    log.info("Call {} with args {} ", joinPoint.getSignature().getName(), args);
    }
    @AfterReturning(value = "callController()", returning = "object")
    public void logReturning(JoinPoint joinPoint, ResponseEntity<?> object){
        log.info("Call {} with returning {}", joinPoint.getSignature().getName(), object.getBody());
    }
}

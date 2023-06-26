package ru.clevertec.statkevich.starterlogging.logging.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    @Around(value = "within(@org.springframework.web.bind.annotation.RestController *)")
    public Object get(ProceedingJoinPoint pjp) throws Throwable {

        String requestedClass = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        String loggingMessage = String.format("""
                        Requested class:  %s
                        Method name: %s
                        Execution Time: %x ms""",
                requestedClass,
                methodName,
                executionTime);

        log.info(loggingMessage);

        return proceed;
    }
}

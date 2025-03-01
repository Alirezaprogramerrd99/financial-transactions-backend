package org.example.financial_transactions.controller.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggerAspect {

    @Before("execution(* org.example.financial_transaction.controller.AccountController.*(..)) || " +
            "execution(* org.example.financial_transaction.controller.TransactionController.*(..)) || " +
            "execution(* org.example.financial_transaction.controller.CustomerController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method executed: {}", methodName);
    }
}
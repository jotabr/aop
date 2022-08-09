package br.eti.jjd.aop_exemplo.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LivroControllerAspect {

    private static final Logger LOGGER = LogManager.getLogger(LivroControllerAspect.class);

    @Pointcut("execution(* br.eti.jjd.aop_exemplo.web.LivroController.createLivro(..))")
    public void repositoryMethods() {};

    @Before("repositoryMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        LOGGER.info("Before> Method: " + joinPoint.getSignature().getName());
        LOGGER.info("Before> Args.: " + joinPoint.getArgs()[0].toString());
    }

    @Pointcut("execution(* br.eti.jjd.aop_exemplo.model.LivroRepository.deleteById(..))))")
    public void deuBom() {};

    @AfterReturning("deuBom()")
    public void logAfterDeletingConta(JoinPoint jp) {
        LOGGER.info("After>> Apagou na boa >> " + jp.getArgs()[0].toString());
    }

    //@Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.deleteById(..))))")
    @Pointcut("execution(* br.eti.jjd.aop_exemplo.model.LivroRepository.deleteById(..))))")
    public void deuRuim() {};

    @AfterThrowing(pointcut = "deuRuim()", throwing = "ex")
    public void logAfterThrowing(Exception ex) throws Throwable {
        LOGGER.info("AfterThrowing: " + ex);
    }

    @Around("execution(* br.eti.jjd.aop_exemplo.web..*(..)))")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        final StopWatch stopWatch = new StopWatch();

        //Calcula o tempo
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Tempo de execução
        LOGGER.info("Tempo de execução de "
                + methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName() + " "
                + ":: " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }

}

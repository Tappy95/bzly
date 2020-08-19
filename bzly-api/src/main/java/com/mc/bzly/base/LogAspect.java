package com.mc.bzly.base;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);
	@Pointcut("execution(* com.mc.bzly.controller.*.*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void deBefore(JoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url={}", request.getRequestURI());
        logger.info("method={}", request.getMethod());
        logger.info("ip={}",IPUtil.getIp(request));
        logger.info("class={} and method name = {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        logger.info("param={}",joinPoint.getArgs());
        logger.info("token:{}",request.getParameter("token"));
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		//logger.info("return : " + ret);
	}

	@AfterThrowing("webLog()")
	public void throwss(JoinPoint jp) {
		logger.info("exception exce.....");
	}

	@After("webLog()")
	public void after(JoinPoint jp) {
		logger.info("final exce.....");
	}

	@Around("webLog()")
	public Object arround(ProceedingJoinPoint pjp) {
		logger.info("arround start.....");
		try {
			Object o = pjp.proceed();
			//logger.info(" arround proceed，result :" + o);
			return o;
		} catch (Throwable e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}

package com.dev.log.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.dev.log.domain.BaseVO;
import com.dev.log.domain.HeaderVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class HeaderAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object aroundController(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs(); //request body
        Object returnValue = joinPoint.proceed();  //response 

        if (returnValue != null) {
            ResponseEntity<?> resEntity = (ResponseEntity<?>) returnValue;
            Object body = resEntity.getBody();

            BaseVO ansVO = null;

            // response header 설정
            if (!ObjectUtils.isEmpty(body) && body instanceof BaseVO) { 
                ansVO = (BaseVO)body;

                if(ansVO.getHeader() == null && !ObjectUtils.isEmpty(args)) { //응답 header가 null인 경우
                    for(Object arg : args) {
                        if(arg instanceof BaseVO) {
                            BaseVO ivo = (BaseVO)arg; //BaseVO를 상속 받은 경우 

                            HeaderVO ivoHeader = ivo.getHeader(); //request body 의 header 
                            ansVO.setHeader(ivoHeader); // response body의 header에 set

                            break;
                        }
                    }
                }
            } else {
                log.warn("body is not instance of BaseVO");
            }

        }

        return returnValue;
    }
}

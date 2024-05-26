package me.cerrato.w2m.spaceships.domain.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class ApiNegativeIdInterceptor {

    @Before(value = "execution(* me.cerrato.w2m.spaceships.api.controllers.ApiController.findById(..)) && args(id)", argNames = "id")
    public void interceptNegativeValues(Long id) throws Throwable {
        if (id < 0) {
            log.error("Id is negative value {}", id);
        }
    }

}
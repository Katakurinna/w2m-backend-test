package me.cerrato.w2m.spaceships.domain.interceptors;

import lombok.extern.slf4j.Slf4j;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.domain.utils.ApplicationConfig;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import me.cerrato.w2m.spaceships.infra.rabbit.services.RabbitMQService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RabbitCreationInterceptor {

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private RabbitMQService rabbitMQService;
    @Autowired
    private ConversionService conversionService;

    @AfterReturning(value = "execution(* me.cerrato.w2m.spaceships.domain.services.SpaceshipService.save(..))", returning = "spaceship")
    public SpaceshipEntity interceptCreatedEntities(SpaceshipEntity spaceship) throws Throwable {
        // Disable interceptor for testing without rabbitmq server.
        if (!config.getInterceptRabbitMessages()) return spaceship;

        log.debug("Created new entity {}, sended to rabbit queue", spaceship);
        Spaceship dto = conversionService.convert(spaceship, Spaceship.class);
        rabbitMQService.sendCreatedSpaceship(dto);
        return spaceship;
    }
}

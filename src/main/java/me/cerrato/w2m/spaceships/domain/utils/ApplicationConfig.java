package me.cerrato.w2m.spaceships.domain.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
@PropertySource(value = "classpath:application.properties")
public class ApplicationConfig {

    @Value("${spaceship.database.page.size}")
    private Integer databasePageSize;

    @Value("${spaceship.rabbit.intercept:true}")
    private Boolean interceptRabbitMessages;

}
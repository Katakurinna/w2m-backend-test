package me.cerrato.w2m.spaceships.infra.rabbit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityAlreadyExistException;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.domain.services.SpaceshipService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Bean
    public Queue createdSpaceships() {
        return new Queue("createdSpaceships", true);
    }

    @Bean
    public Queue createSpaceships() {
        return new Queue("spaceship-creation", true);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SpaceshipService spaceshipService;

    @SneakyThrows
    public void sendCreatedSpaceship(Spaceship spaceship) {
        rabbitTemplate.convertAndSend("createdSpaceships", mapper.writeValueAsString(spaceship));
    }

    @SneakyThrows
    @RabbitListener(queues = {"spaceship-creation"}, ackMode = "NONE")
    public void createSpaceship(String message) {
        Spaceship spaceship = mapper.readValue(message, Spaceship.class);
        try {
        spaceshipService.save(spaceship);
        } catch (Exception ex) {
            // No exception control, add it.
        }
    }

}
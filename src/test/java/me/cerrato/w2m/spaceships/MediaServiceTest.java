package me.cerrato.w2m.spaceships;

import jakarta.transaction.Transactional;
import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.MediaType;
import me.cerrato.w2m.spaceships.domain.services.MediaService;
import me.cerrato.w2m.spaceships.infra.db.repositories.MediaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MediaServiceTest {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaRepository mediaRepository;

    @BeforeEach
    @Transactional
    void createData() {
        mediaService.save(new Media("Test", MediaType.MOVIE));
    }


    @AfterEach
    @Transactional
    void cleanData() {
        // Don't add these method to services, will remove all database and cannot be used in production services.
        mediaRepository.deleteAll();
    }

    @Test
    public void whenAddingExistingMedia_thenReturnException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> mediaService.save(new Media("Test", MediaType.MOVIE)));
    }

    @Test
    public void whenAddingMediaWithoutName_thenReturnException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> mediaService.save(new Media("", MediaType.MOVIE)));
    }

    @Test
    public void whenAddingMediaWithoutMediaType_thenReturnException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> mediaService.save(new Media("Test", null)));
    }

}
package me.cerrato.w2m.spaceships;

import jakarta.transaction.Transactional;
import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.MediaType;
import me.cerrato.w2m.spaceships.domain.services.MediaService;
import me.cerrato.w2m.spaceships.domain.services.SpaceshipService;
import me.cerrato.w2m.spaceships.infra.db.model.MediaEntity;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import me.cerrato.w2m.spaceships.infra.db.repositories.MediaRepository;
import me.cerrato.w2m.spaceships.infra.db.repositories.SpaceshipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpaceRepositoryTest {

    @Autowired
    private SpaceshipRepository spaceshipRepository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    SpaceshipService service;

    @BeforeEach
    @Transactional
    void createData() {
        MediaEntity media = mediaService.save(new Media("Test", MediaType.MOVIE));
        spaceshipRepository.save(new SpaceshipEntity("Test1", media));
        spaceshipRepository.save(new SpaceshipEntity("Test2", media));
        spaceshipRepository.save(new SpaceshipEntity("Test3", media));
        spaceshipRepository.save(new SpaceshipEntity("Test4", media));
        spaceshipRepository.save(new SpaceshipEntity("Test5", media));
    }

    @AfterEach
    @Transactional
    void cleanData() {
        // Don't add these method to services, will remove all database and cannot be used in production services.
        spaceshipRepository.deleteAll();
        mediaRepository.deleteAll();
    }

    @Test
    public void whenFindByNameContainingNumberFive_thenReturnOnlyOneEntity() {
        List<SpaceshipEntity> collect = spaceshipRepository.findAllByNameContaining("5");
        Assertions.assertEquals(1, collect.size());
    }

    @Test
    public void whenFindByNameContainingTest_thenReturnAllEntities() {
        List<SpaceshipEntity> collect = spaceshipRepository.findAllByNameContaining("Test");
        Assertions.assertEquals(5, collect.size());
    }

    @Test
    public void whenFindByNameContainingNotValidText_thenReturnZeroEntities() {
        List<SpaceshipEntity> collect = spaceshipRepository.findAllByNameContaining("Text");
        Assertions.assertEquals(0, collect.size());
    }

}
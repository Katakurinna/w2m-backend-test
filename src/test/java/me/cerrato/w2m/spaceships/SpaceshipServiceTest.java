package me.cerrato.w2m.spaceships;

import jakarta.transaction.Transactional;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityAlreadyExistException;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityDontExistException;
import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.MediaType;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.domain.services.SpaceshipService;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import me.cerrato.w2m.spaceships.infra.db.repositories.SpaceshipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SpaceshipServiceTest {

    @Autowired
    private SpaceshipService service;

    @Autowired
    private SpaceshipRepository repository;

    @AfterEach
    @Transactional
    void cleanData() {
        // Don't add these method to services, will remove all database and cannot be used in production services.
        repository.deleteAll();
    }

    @Test
    public void whenSaveNewSpaceShip_thenReturnEntityWithSameValues() throws EntityAlreadyExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));
        assertEquals(spaceshipName, entity.getName());
        assertEquals(mediaName, entity.getMediaEntity().getName());
        assertEquals(mediaType, entity.getMediaEntity().getType());

    }

    @Test
    public void whenSaveExistingSpaceship_thenReturnException() throws EntityAlreadyExistException {
        service.save(new Spaceship("Test", new Media("Test-Film", MediaType.MOVIE)));
        assertThrows(
                EntityAlreadyExistException.class,
                () -> service.save(new Spaceship("Test", new Media("Test-Film", MediaType.MOVIE)))
        );
    }

    @Test
    public void whenSaveSpaceshipWithNullValues_thenReturnException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.save(new Spaceship(null, null))
        );
    }

    @Test
    public void whenEditSpaceshipWithNewNameValue_thenReturnEntityWithSameValues() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String editedSpaceshipName = "no-test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;

        // Its really needed check if entity is saved OK? this will be tested in upper tests.
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        SpaceshipEntity editedEntity = service.edit(entity.getId(), new Spaceship(editedSpaceshipName, null));
        assertEquals(editedSpaceshipName, editedEntity.getName());
        assertEquals(mediaName, editedEntity.getMediaEntity().getName());
        assertEquals(mediaType, editedEntity.getMediaEntity().getType());
    }

    @Test
    public void whenEditSpaceshipWithNewMediaValue_thenReturnEntityWithSameValues() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        String editedMediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;

        // Its really needed check if entity is saved OK? this will be tested in upper tests.
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        SpaceshipEntity editedEntity = service.edit(entity.getId(), new Spaceship(spaceshipName, new Media(editedMediaName, mediaType)));
        assertEquals(spaceshipName, editedEntity.getName());
        assertEquals(editedMediaName, editedEntity.getMediaEntity().getName());
        assertEquals(mediaType, editedEntity.getMediaEntity().getType());
    }

    @Test
    public void whenEditSpaceshipWithNoValues_thenReturnException() throws EntityAlreadyExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;

        // Its really needed check if entity is saved OK? this will be tested in upper tests.
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.edit(entity.getId(), new Spaceship(null, null))
        );
    }

    @Test
    public void whenEditNonExistingSpaceship_thenReturnException() {
        assertThrows(
                EntityDontExistException.class,
                () -> service.edit(-1, new Spaceship("error", null))
        );
    }

    @Test
    public void whenRemoveExistingSpaceship_thenReturnNothing() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;

        // Its really needed check if entity is saved OK? this will be tested in upper tests.
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));
        service.delete(entity.getId());

    }

    @Test
    public void whenRemoveNonExistingSpaceship_thenReturnException() {
        assertThrows(
                EntityDontExistException.class,
                () -> service.delete(-1)
        );
    }

    @Test
    public void whenFindByIdExist_thenReturnEntity() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        SpaceshipEntity searchedEntity = service.findById(entity.getId());

        assertEquals(entity.getId(), searchedEntity.getId());
    }

    @Test
    public void whenFindByIdNonExist_thenReturnException() {
        assertThrows(
                EntityDontExistException.class,
                () -> service.findById(-1)
        );
    }

    @Test
    public void whenFindByNameContainingExist_thenReturnEntity() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        List<SpaceshipEntity> searchedEntities = service.findByNameContaining("es");

        assertEquals(1, searchedEntities.size());
        assertEquals(entity.getId(), searchedEntities.get(0).getId());
    }

    @Test
    public void whenFindByNameContainingExist_thenReturnZeroElements() {
        assertEquals(
                0,
                service.findByNameContaining("es").size()
        );
    }

    @Test
    public void whenFindAllExist_thenReturnEntity() throws EntityAlreadyExistException, EntityDontExistException {
        String spaceshipName = "test";
        String mediaName = "Test-Film";
        MediaType mediaType = MediaType.MOVIE;
        SpaceshipEntity entity = service.save(new Spaceship(spaceshipName, new Media(mediaName, mediaType)));

        List<SpaceshipEntity> searchedEntities = service.findAll(1);

        assertEquals(1, searchedEntities.size());
        assertEquals(entity.getId(), searchedEntities.get(0).getId());
    }

    @Test
    public void whenFindAllExist_thenReturnZeroElements() {
        assertEquals(
                0,
                service.findAll(1).size()
        );
    }

}
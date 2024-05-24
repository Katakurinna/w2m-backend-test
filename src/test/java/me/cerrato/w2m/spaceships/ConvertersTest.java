package me.cerrato.w2m.spaceships;

import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.MediaType;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.infra.db.model.MediaEntity;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class ConvertersTest {

    @Autowired
    private ConversionService service;

    @Test
    public void testMediaEntityToMediaConversor() {
        String name = "test";
        MediaType type = MediaType.MOVIE;
        MediaEntity entity = new MediaEntity(1L, name, type);

        Media convert = service.convert(entity, Media.class);
        Assertions.assertEquals(name, convert.getName());
        Assertions.assertEquals(type, convert.getType());
    }

    @Test
    public void testSpaceShipEntityToMediaConversor() {
        String mediaName = "test";
        MediaType type = MediaType.MOVIE;
        MediaEntity mediaEntity = new MediaEntity(1L, mediaName, type);
        long id = 1L;
        String name = "spaceship";
        SpaceshipEntity spaceshipEntity = new SpaceshipEntity(id, name, mediaEntity);

        Spaceship convert = service.convert(spaceshipEntity, Spaceship.class);
        Assertions.assertEquals(id, convert.getId());
        Assertions.assertEquals(name, convert.getName());
        Assertions.assertEquals(type, convert.getMedia().getType());
    }
}

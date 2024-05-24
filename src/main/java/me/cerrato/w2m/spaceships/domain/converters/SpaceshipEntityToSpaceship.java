package me.cerrato.w2m.spaceships.domain.converters;

import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipEntityToSpaceship extends AutoregisteringConverter<SpaceshipEntity, Spaceship> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public Spaceship convert(SpaceshipEntity source) {
        Media media = conversionService.convert(source.getMediaEntity(), Media.class);
        return new Spaceship(source.getId(), source.getName(), media);
    }
}

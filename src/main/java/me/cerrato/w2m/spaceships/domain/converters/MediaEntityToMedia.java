package me.cerrato.w2m.spaceships.domain.converters;

import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.infra.db.model.MediaEntity;
import org.springframework.stereotype.Component;

@Component
public class MediaEntityToMedia extends AutoregisteringConverter<MediaEntity, Media> {

    @Override
    public Media convert(MediaEntity source) {
        return new Media(source.getName(), source.getType());
    }

}
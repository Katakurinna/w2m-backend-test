package me.cerrato.w2m.spaceships.domain.services;

import jakarta.transaction.Transactional;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityDontExistException;
import me.cerrato.w2m.spaceships.domain.models.Media;
import me.cerrato.w2m.spaceships.domain.models.MediaType;
import me.cerrato.w2m.spaceships.infra.db.model.MediaEntity;
import me.cerrato.w2m.spaceships.infra.db.repositories.MediaRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediaService {

    @Autowired
    private MediaRepository repository;

    @Transactional
    @CachePut(value = "media", key = "#media.id")
    public MediaEntity save(Media media) {
        if (Strings.isEmpty(media.getName()) || media.getType() == null) {
            throw new IllegalArgumentException("Given media type or media's name are empty");
        }
        MediaEntity entity = new MediaEntity(media.getName(), media.getType());
        Example<MediaEntity> example = Example.of(entity);
        if (repository.exists(example)) {
            throw new IllegalArgumentException("Media already exist");
        }

        return repository.save(entity);
    }

    /**
     * Find a media using name and type, if not exist, return EntityDontExistException
     *
     * @param name name of the media. eg: StarWars
     * @param type type of the media. see {@link MediaType} types.
     * @return a media if exist (never nulls)
     * @throws EntityDontExistException exception if dont exist any media
     */
    @Cacheable("media")
    public MediaEntity findByNameAndType(String name, MediaType type) throws EntityDontExistException {
        Optional<MediaEntity> optionalMedia = repository.findByNameAndType(name, type);
        if (optionalMedia.isEmpty()) {
            throw new EntityDontExistException("Media don't exist");
        }
        return optionalMedia.get();
    }

    /**
     * Find the media if exist or create a new one
     *
     * @param media media with name and type of media
     * @return existing media
     */
    @Cacheable("media")
    public MediaEntity findOrCreate(Media media) {
        try {
            return findByNameAndType(media.getName(), media.getType());
        } catch (EntityDontExistException e) {
            return save(media);
        }
    }
}
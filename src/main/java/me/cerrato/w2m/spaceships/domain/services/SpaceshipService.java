package me.cerrato.w2m.spaceships.domain.services;

import jakarta.transaction.Transactional;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityAlreadyExistException;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityDontExistException;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.domain.utils.ApplicationConfig;
import me.cerrato.w2m.spaceships.infra.db.model.MediaEntity;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import me.cerrato.w2m.spaceships.infra.db.repositories.SpaceshipRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipService {

    @Autowired
    private SpaceshipRepository repository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private ApplicationConfig config;


    @Transactional
    @CachePut(value = "spaceships", key = "#spaceships.id")
    public SpaceshipEntity save(Spaceship spaceship) throws EntityAlreadyExistException {
        if (Strings.isEmpty(spaceship.getName()) || spaceship.getMedia() == null) {
            throw new IllegalArgumentException("Given spaceship type or spaceship's name are empty");
        }

        MediaEntity media = mediaService.findOrCreate(spaceship.getMedia());
        SpaceshipEntity entity = new SpaceshipEntity(spaceship.getName(), media);
        Example<SpaceshipEntity> example = Example.of(entity);
        if (repository.exists(example)) {
            throw new EntityAlreadyExistException("Spaceship already exist");
        }

        return repository.save(entity);
    }

    @Transactional
    @CachePut(value = "spaceships", key = "#spaceships.id")
    public SpaceshipEntity edit(Spaceship spaceship) throws EntityDontExistException {
        if (Strings.isEmpty(spaceship.getName()) && spaceship.getMedia() == null) {
            throw new IllegalArgumentException("Given spaceship type and spaceship's name are empty");
        }
        Optional<SpaceshipEntity> optionalSpaceship = repository.findById(spaceship.getId());
        if (optionalSpaceship.isEmpty()) {
            throw new EntityDontExistException("Spaceship don't exist");
        }

        SpaceshipEntity spaceshipEntity = optionalSpaceship.get();

        if (!Strings.isEmpty(spaceship.getName())) {
            spaceshipEntity.setName(spaceship.getName());
        }
        if (spaceship.getMedia() != null) {
            MediaEntity media = mediaService.findOrCreate(spaceship.getMedia());
            spaceshipEntity.setMediaEntity(media);
        }

        return repository.save(spaceshipEntity);
    }

    /**
     * Delete a spaceship by id
     * @param id id of the spaceship to delete
     * @throws EntityDontExistException
     */
    @Transactional
    @CacheEvict(value = "spaceships", key = "#spaceships.id")
    public void delete(long id) throws EntityDontExistException {
        if (!repository.existsById(id)) {
            throw new EntityDontExistException("Spaceship don't exist");
        }
        repository.deleteById(id);
    }

    /**
     * Search a spaceship by id
     *
     * @param id id of the spaceship
     * @return a spaceship if exist
     * @throws EntityDontExistException exception if spaceship don't exist
     */
    @Cacheable("spaceships")
    public SpaceshipEntity findById(long id) throws EntityDontExistException {
        Optional<SpaceshipEntity> optionalSpaceship = repository.findById(id);
        if (optionalSpaceship.isEmpty()) {
            throw new EntityDontExistException("Spaceship don't exist");
        }

        return optionalSpaceship.get();
    }

    /**
     * Search a list of spaceships by name containing
     *
     * @param nameContaining the part of the name to search.
     * @return a list of spaceships with the part of the name, or empty list if no results.
     */
    @Cacheable("spaceships")
    public List<SpaceshipEntity> findByNameContaining(String nameContaining) {
        return repository.findAllByNameContaining(nameContaining);
    }

    /**
     * Find all by page, minimum page is 1, see {@link ApplicationConfig#databasePageSize}
     * @param page page number, lower value is one
     * @return a list of spaceships, or empty list if no results.
     */
    @Cacheable("spaceships")
    public List<SpaceshipEntity> findAll(int page) {
        Page<SpaceshipEntity> spaceshipPage = repository.findAll(PageRequest.of(page - 1, config.getDatabasePageSize()));
        return spaceshipPage.toList();
    }

}

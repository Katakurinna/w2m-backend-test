package me.cerrato.w2m.spaceships.api.controllers;

import me.cerrato.w2m.spaceships.api.models.ListedResponse;
import me.cerrato.w2m.spaceships.api.models.PageableResponse;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityAlreadyExistException;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityDontExistException;
import me.cerrato.w2m.spaceships.domain.models.Spaceship;
import me.cerrato.w2m.spaceships.domain.services.SpaceshipService;
import me.cerrato.w2m.spaceships.domain.utils.ApplicationConfig;
import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spaceship")
public class ApiController {

    @Autowired
    private SpaceshipService service;
    @Autowired
    private ConversionService conversionService;

    /**
     * Find all spaceships by page, if page is null, you will get the first page
     *
     * @param page page number
     * @return A page with {@link ApplicationConfig#databasePageSize} spaceships
     */
    @GetMapping("/all")
    public PageableResponse<Spaceship> findAll(@RequestParam(name = "page", required = false) Integer page) {
        int pageNumber = page == null ? 1 : page;

        List<Spaceship> spaceships = service.findAll(pageNumber).stream()
                .map(s -> conversionService.convert(s, Spaceship.class))
                .toList();

        return new PageableResponse<>(spaceships, pageNumber);
    }

    /**
     * Search a spaceship by id
     *
     * @param id spaceship's id
     * @return a spaceship if exist
     * @throws EntityDontExistException if spaceship does not exist
     */
    @GetMapping("/{id}")
    public Spaceship findById(@PathVariable("id") Integer id) throws EntityDontExistException {
        SpaceshipEntity entity = service.findById(id);
        return conversionService.convert(entity, Spaceship.class);
    }

    /**
     * Search all spaceship's with containing name. Return a list with all entities
     *
     * @param name containing name to search
     * @return A list with spaceships of empty list of no one contains the name
     */
    @GetMapping("/contains/{name}")
    public ListedResponse<Spaceship> findByNameLike(@PathVariable("name") String name) {
        List<Spaceship> spaceships = service.findByNameContaining(name).stream()
                .map(s -> conversionService.convert(s, Spaceship.class))
                .toList();
        return new ListedResponse<>(spaceships);
    }

    /**
     * Edit a spaceship by id
     *
     * @param request id of the spaceship to end and new spaceship's information
     * @throws EntityDontExistException If spaceship does not exist
     */
    @PutMapping("/edit/{id}")
    public void modify(@RequestBody Spaceship request) throws EntityDontExistException {
        service.edit(request);
    }

    /**
     * Create a new spaceship
     *
     * @param request Hero's info
     * @return Created spaceship's data, with ID
     * @throws EntityAlreadyExistException If spaceship already exist
     */
    @PostMapping("/create")
    public Spaceship create(@RequestBody Spaceship request) throws EntityAlreadyExistException {
        SpaceshipEntity spaceship = service.save(request);

        return conversionService.convert(spaceship, Spaceship.class);
    }

    /**
     * Remove a spaceship if exist
     *
     * @param id spaceship's id
     * @throws EntityDontExistException if spaceship does not exist
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) throws EntityDontExistException {
        service.delete(id);
    }

}
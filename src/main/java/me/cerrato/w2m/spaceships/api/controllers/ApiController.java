package me.cerrato.w2m.spaceships.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.cerrato.w2m.spaceships.api.models.ErrorResponse;
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
    @Operation(summary = "Find all spaceships paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of spaceships with 0 or N results",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageableResponse.class))
                    })
    })
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
    @Operation(summary = "Find one spaceship by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the spaceship",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Spaceship.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Spaceship not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
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
    @Operation(summary = "Find list of spaceships by part of a name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of spaceship with 0 or N spaceships",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListedResponse.class))
                    })
    })
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
    @Operation(summary = "Edit a spaceship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship modified"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Cannot edit spaceship without new values",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PutMapping("/{id}")
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
    @Operation(summary = "Create a spaceship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship created"),
            @ApiResponse(responseCode = "409", description = "Spaceship already exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Cannot create spaceship without all values",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping()
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
    @Operation(summary = "Delete a spaceship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship deleted"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) throws EntityDontExistException {
        service.delete(id);
    }

}
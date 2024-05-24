package me.cerrato.w2m.spaceships.infra.db.repositories;

import me.cerrato.w2m.spaceships.infra.db.model.SpaceshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Long> {

    Page<SpaceshipEntity> findAll(Pageable page);

    List<SpaceshipEntity> findAllByNameContaining(String containing);
}

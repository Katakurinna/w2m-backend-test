package me.cerrato.w2m.spaceships.infra.db.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "spaceship")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class SpaceshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "media")
    private MediaEntity mediaEntity;

}
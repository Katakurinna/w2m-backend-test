package me.cerrato.w2m.spaceships.infra.db.model;

import jakarta.persistence.*;
import lombok.*;
import me.cerrato.w2m.spaceships.domain.models.MediaType;

@Getter
@Entity
@Table(name = "media")
@NoArgsConstructor
@RequiredArgsConstructor
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "type", nullable = false)
    private MediaType type;

}
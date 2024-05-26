package me.cerrato.w2m.spaceships.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Spaceship {

    private Long id;

    private String name;

    private Media media;

}
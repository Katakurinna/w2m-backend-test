package me.cerrato.w2m.spaceships.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Spaceship {

    private Long id;

    private String name;

    private Media media;

}
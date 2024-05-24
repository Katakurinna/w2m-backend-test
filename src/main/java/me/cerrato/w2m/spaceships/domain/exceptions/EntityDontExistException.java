package me.cerrato.w2m.spaceships.domain.exceptions;

public class EntityDontExistException extends Exception {
    public EntityDontExistException(String message) {
        super(message);
    }

}
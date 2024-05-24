package me.cerrato.w2m.spaceships.api.controllers;

import lombok.extern.slf4j.Slf4j;
import me.cerrato.w2m.spaceships.api.models.ErrorResponse;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityAlreadyExistException;
import me.cerrato.w2m.spaceships.domain.exceptions.EntityDontExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityDontExistException.class})
    public ResponseEntity<ErrorResponse> handleEntityDontExistException(Exception ex, WebRequest request) {
        log.debug("Spaceship not found", ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({EntityAlreadyExistException.class})
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistException(Exception ex, WebRequest request) {
        log.debug("Spaceship already exist", ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {
        log.debug("Given information are empty", ex);
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

}
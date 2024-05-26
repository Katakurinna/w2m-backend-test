package me.cerrato.w2m.spaceships.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Cause of the error")
public class ErrorResponse {

    private String cause;

}
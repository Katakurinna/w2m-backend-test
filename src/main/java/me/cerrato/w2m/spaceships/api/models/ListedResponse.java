package me.cerrato.w2m.spaceships.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "List of results")
public class ListedResponse<R> {

    @NonNull
    @Schema(description = "Obtained items")
    protected List<R> items;

}
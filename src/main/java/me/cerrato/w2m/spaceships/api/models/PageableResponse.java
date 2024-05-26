package me.cerrato.w2m.spaceships.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "List of results paged")
public class PageableResponse<R> extends ListedResponse<R> {

    @NonNull
    @Schema(description = "Number of page", defaultValue = "1")
    private Integer page;

    // That's constructor exist because lombok don't permit super class attributes constructor
    public PageableResponse(List<R> items, Integer page) {
        super(items);
        this.page = page;
    }

}
package me.cerrato.w2m.spaceships.api.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PageableResponse<R> extends ListedResponse<R> {

    @NonNull
    private Integer page;

    // That's constructor exist because lombok don't permit super class attributes constructor
    public PageableResponse(List<R> items, Integer page) {
        super(items);
        this.page = page;
    }

}
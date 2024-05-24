package me.cerrato.w2m.spaceships.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListedResponse<R> {

    @NonNull
    protected List<R> items;

}
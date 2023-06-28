package com.application.movieBase.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

public class CollectionMapper {
    public static <E, D> List<D> mapEntitiesToDTOs(Page<E> entities, Function<E, D> mapper) {
        return entities.stream().map(mapper).collect(Collectors.toCollection(ArrayList::new));
    }
}

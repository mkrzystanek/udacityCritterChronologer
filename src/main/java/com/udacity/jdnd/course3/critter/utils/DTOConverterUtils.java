package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.EntityInterface;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DTOConverterUtils {

    public <T> Collection<T> convertIdListToEntityCollection(List<Long> ids, Function<Long, T> getEntity) {
        return ids.stream()
                .map(id -> {
                    try {
                        return getEntity.apply(id);
                    } catch (Exception e){
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Collection<Long> convertEntitiesToIdList(Collection<? extends EntityInterface> entities) {
        return entities.stream()
                .map(EntityInterface::getId)
                .collect(Collectors.toList());
    }
}

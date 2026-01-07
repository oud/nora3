package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RechercheSolvable;
import com.mycompany.myapp.domain.ResultatSolvable;
import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
import com.mycompany.myapp.service.dto.ResultatSolvableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultatSolvable} and its DTO {@link ResultatSolvableDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResultatSolvableMapper extends EntityMapper<ResultatSolvableDTO, ResultatSolvable> {
    @Mapping(target = "rechercheSolvable", source = "rechercheSolvable", qualifiedByName = "rechercheSolvableId")
    ResultatSolvableDTO toDto(ResultatSolvable s);

    @Named("rechercheSolvableId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RechercheSolvableDTO toDtoRechercheSolvableId(RechercheSolvable rechercheSolvable);
}

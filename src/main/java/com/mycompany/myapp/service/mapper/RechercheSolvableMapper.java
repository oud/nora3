package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.domain.RechercheSolvable;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RechercheSolvable} and its DTO {@link RechercheSolvableDTO}.
 */
@Mapper(componentModel = "spring")
public interface RechercheSolvableMapper extends EntityMapper<RechercheSolvableDTO, RechercheSolvable> {
    @Mapping(target = "debiteur", source = "debiteur", qualifiedByName = "debiteurId")
    RechercheSolvableDTO toDto(RechercheSolvable s);

    @Named("debiteurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DebiteurDTO toDtoDebiteurId(Debiteur debiteur);
}

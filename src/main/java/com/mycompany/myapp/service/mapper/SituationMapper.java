package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.domain.Situation;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.service.dto.SituationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Situation} and its DTO {@link SituationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SituationMapper extends EntityMapper<SituationDTO, Situation> {
    @Mapping(target = "debiteur", source = "debiteur", qualifiedByName = "debiteurId")
    SituationDTO toDto(Situation s);

    @Named("debiteurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DebiteurDTO toDtoDebiteurId(Debiteur debiteur);
}

package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.domain.Enfant;
import com.mycompany.myapp.service.dto.DossierDTO;
import com.mycompany.myapp.service.dto.EnfantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enfant} and its DTO {@link EnfantDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnfantMapper extends EntityMapper<EnfantDTO, Enfant> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "dossierId")
    EnfantDTO toDto(Enfant s);

    @Named("dossierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DossierDTO toDtoDossierId(Dossier dossier);
}

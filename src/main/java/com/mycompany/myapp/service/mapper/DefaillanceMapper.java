package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Defaillance;
import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.service.dto.DefaillanceDTO;
import com.mycompany.myapp.service.dto.DossierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Defaillance} and its DTO {@link DefaillanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface DefaillanceMapper extends EntityMapper<DefaillanceDTO, Defaillance> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "dossierId")
    DefaillanceDTO toDto(Defaillance s);

    @Named("dossierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DossierDTO toDtoDossierId(Dossier dossier);
}

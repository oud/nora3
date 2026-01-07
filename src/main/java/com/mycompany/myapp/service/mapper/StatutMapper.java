package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.domain.Statut;
import com.mycompany.myapp.service.dto.DossierDTO;
import com.mycompany.myapp.service.dto.StatutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Statut} and its DTO {@link StatutDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatutMapper extends EntityMapper<StatutDTO, Statut> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "dossierId")
    StatutDTO toDto(Statut s);

    @Named("dossierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DossierDTO toDtoDossierId(Dossier dossier);
}

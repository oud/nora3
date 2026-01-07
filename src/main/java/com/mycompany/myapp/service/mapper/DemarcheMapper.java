package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Demarche;
import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.service.dto.DemarcheDTO;
import com.mycompany.myapp.service.dto.DossierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Demarche} and its DTO {@link DemarcheDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemarcheMapper extends EntityMapper<DemarcheDTO, Demarche> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "dossierId")
    DemarcheDTO toDto(Demarche s);

    @Named("dossierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DossierDTO toDtoDossierId(Dossier dossier);
}

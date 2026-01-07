package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Creancier;
import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.service.dto.CreancierDTO;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.service.dto.DossierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dossier} and its DTO {@link DossierDTO}.
 */
@Mapper(componentModel = "spring")
public interface DossierMapper extends EntityMapper<DossierDTO, Dossier> {
    @Mapping(target = "debiteur", source = "debiteur", qualifiedByName = "debiteurId")
    @Mapping(target = "creancier", source = "creancier", qualifiedByName = "creancierId")
    DossierDTO toDto(Dossier s);

    @Named("debiteurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DebiteurDTO toDtoDebiteurId(Debiteur debiteur);

    @Named("creancierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CreancierDTO toDtoCreancierId(Creancier creancier);
}

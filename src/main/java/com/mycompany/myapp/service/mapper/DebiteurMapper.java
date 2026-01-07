package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Debiteur} and its DTO {@link DebiteurDTO}.
 */
@Mapper(componentModel = "spring")
public interface DebiteurMapper extends EntityMapper<DebiteurDTO, Debiteur> {}

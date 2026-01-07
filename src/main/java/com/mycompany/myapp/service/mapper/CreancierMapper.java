package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Creancier;
import com.mycompany.myapp.service.dto.CreancierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Creancier} and its DTO {@link CreancierDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreancierMapper extends EntityMapper<CreancierDTO, Creancier> {}

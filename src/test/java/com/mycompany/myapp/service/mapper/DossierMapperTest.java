package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DossierAsserts.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DossierMapperTest {

    private DossierMapper dossierMapper;

    @BeforeEach
    void setUp() {
        dossierMapper = new DossierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDossierSample1();
        var actual = dossierMapper.toEntity(dossierMapper.toDto(expected));
        assertDossierAllPropertiesEquals(expected, actual);
    }
}

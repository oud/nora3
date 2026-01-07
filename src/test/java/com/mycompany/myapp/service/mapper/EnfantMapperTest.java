package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.EnfantAsserts.*;
import static com.mycompany.myapp.domain.EnfantTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnfantMapperTest {

    private EnfantMapper enfantMapper;

    @BeforeEach
    void setUp() {
        enfantMapper = new EnfantMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEnfantSample1();
        var actual = enfantMapper.toEntity(enfantMapper.toDto(expected));
        assertEnfantAllPropertiesEquals(expected, actual);
    }
}

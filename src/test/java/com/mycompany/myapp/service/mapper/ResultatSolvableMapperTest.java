package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ResultatSolvableAsserts.*;
import static com.mycompany.myapp.domain.ResultatSolvableTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultatSolvableMapperTest {

    private ResultatSolvableMapper resultatSolvableMapper;

    @BeforeEach
    void setUp() {
        resultatSolvableMapper = new ResultatSolvableMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getResultatSolvableSample1();
        var actual = resultatSolvableMapper.toEntity(resultatSolvableMapper.toDto(expected));
        assertResultatSolvableAllPropertiesEquals(expected, actual);
    }
}

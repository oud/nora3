package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.RechercheSolvableAsserts.*;
import static com.mycompany.myapp.domain.RechercheSolvableTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RechercheSolvableMapperTest {

    private RechercheSolvableMapper rechercheSolvableMapper;

    @BeforeEach
    void setUp() {
        rechercheSolvableMapper = new RechercheSolvableMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRechercheSolvableSample1();
        var actual = rechercheSolvableMapper.toEntity(rechercheSolvableMapper.toDto(expected));
        assertRechercheSolvableAllPropertiesEquals(expected, actual);
    }
}

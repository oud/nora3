package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.StatutAsserts.*;
import static com.mycompany.myapp.domain.StatutTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatutMapperTest {

    private StatutMapper statutMapper;

    @BeforeEach
    void setUp() {
        statutMapper = new StatutMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStatutSample1();
        var actual = statutMapper.toEntity(statutMapper.toDto(expected));
        assertStatutAllPropertiesEquals(expected, actual);
    }
}

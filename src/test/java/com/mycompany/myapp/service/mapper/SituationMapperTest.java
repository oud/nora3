package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.SituationAsserts.*;
import static com.mycompany.myapp.domain.SituationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SituationMapperTest {

    private SituationMapper situationMapper;

    @BeforeEach
    void setUp() {
        situationMapper = new SituationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSituationSample1();
        var actual = situationMapper.toEntity(situationMapper.toDto(expected));
        assertSituationAllPropertiesEquals(expected, actual);
    }
}

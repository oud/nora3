package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DemarcheAsserts.*;
import static com.mycompany.myapp.domain.DemarcheTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemarcheMapperTest {

    private DemarcheMapper demarcheMapper;

    @BeforeEach
    void setUp() {
        demarcheMapper = new DemarcheMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDemarcheSample1();
        var actual = demarcheMapper.toEntity(demarcheMapper.toDto(expected));
        assertDemarcheAllPropertiesEquals(expected, actual);
    }
}

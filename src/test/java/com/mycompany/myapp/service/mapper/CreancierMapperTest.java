package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CreancierAsserts.*;
import static com.mycompany.myapp.domain.CreancierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreancierMapperTest {

    private CreancierMapper creancierMapper;

    @BeforeEach
    void setUp() {
        creancierMapper = new CreancierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCreancierSample1();
        var actual = creancierMapper.toEntity(creancierMapper.toDto(expected));
        assertCreancierAllPropertiesEquals(expected, actual);
    }
}

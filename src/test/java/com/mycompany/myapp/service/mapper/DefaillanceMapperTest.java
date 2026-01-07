package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DefaillanceAsserts.*;
import static com.mycompany.myapp.domain.DefaillanceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaillanceMapperTest {

    private DefaillanceMapper defaillanceMapper;

    @BeforeEach
    void setUp() {
        defaillanceMapper = new DefaillanceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDefaillanceSample1();
        var actual = defaillanceMapper.toEntity(defaillanceMapper.toDto(expected));
        assertDefaillanceAllPropertiesEquals(expected, actual);
    }
}

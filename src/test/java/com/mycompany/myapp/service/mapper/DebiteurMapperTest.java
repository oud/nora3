package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DebiteurAsserts.*;
import static com.mycompany.myapp.domain.DebiteurTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DebiteurMapperTest {

    private DebiteurMapper debiteurMapper;

    @BeforeEach
    void setUp() {
        debiteurMapper = new DebiteurMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDebiteurSample1();
        var actual = debiteurMapper.toEntity(debiteurMapper.toDto(expected));
        assertDebiteurAllPropertiesEquals(expected, actual);
    }
}

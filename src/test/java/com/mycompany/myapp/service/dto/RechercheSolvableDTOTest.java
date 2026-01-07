package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RechercheSolvableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RechercheSolvableDTO.class);
        RechercheSolvableDTO rechercheSolvableDTO1 = new RechercheSolvableDTO();
        rechercheSolvableDTO1.setId(1L);
        RechercheSolvableDTO rechercheSolvableDTO2 = new RechercheSolvableDTO();
        assertThat(rechercheSolvableDTO1).isNotEqualTo(rechercheSolvableDTO2);
        rechercheSolvableDTO2.setId(rechercheSolvableDTO1.getId());
        assertThat(rechercheSolvableDTO1).isEqualTo(rechercheSolvableDTO2);
        rechercheSolvableDTO2.setId(2L);
        assertThat(rechercheSolvableDTO1).isNotEqualTo(rechercheSolvableDTO2);
        rechercheSolvableDTO1.setId(null);
        assertThat(rechercheSolvableDTO1).isNotEqualTo(rechercheSolvableDTO2);
    }
}

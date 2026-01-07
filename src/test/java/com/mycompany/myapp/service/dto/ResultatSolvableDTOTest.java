package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultatSolvableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatSolvableDTO.class);
        ResultatSolvableDTO resultatSolvableDTO1 = new ResultatSolvableDTO();
        resultatSolvableDTO1.setId(1L);
        ResultatSolvableDTO resultatSolvableDTO2 = new ResultatSolvableDTO();
        assertThat(resultatSolvableDTO1).isNotEqualTo(resultatSolvableDTO2);
        resultatSolvableDTO2.setId(resultatSolvableDTO1.getId());
        assertThat(resultatSolvableDTO1).isEqualTo(resultatSolvableDTO2);
        resultatSolvableDTO2.setId(2L);
        assertThat(resultatSolvableDTO1).isNotEqualTo(resultatSolvableDTO2);
        resultatSolvableDTO1.setId(null);
        assertThat(resultatSolvableDTO1).isNotEqualTo(resultatSolvableDTO2);
    }
}

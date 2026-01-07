package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SituationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituationDTO.class);
        SituationDTO situationDTO1 = new SituationDTO();
        situationDTO1.setId(1L);
        SituationDTO situationDTO2 = new SituationDTO();
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
        situationDTO2.setId(situationDTO1.getId());
        assertThat(situationDTO1).isEqualTo(situationDTO2);
        situationDTO2.setId(2L);
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
        situationDTO1.setId(null);
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
    }
}

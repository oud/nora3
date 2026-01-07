package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemarcheDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemarcheDTO.class);
        DemarcheDTO demarcheDTO1 = new DemarcheDTO();
        demarcheDTO1.setId(1L);
        DemarcheDTO demarcheDTO2 = new DemarcheDTO();
        assertThat(demarcheDTO1).isNotEqualTo(demarcheDTO2);
        demarcheDTO2.setId(demarcheDTO1.getId());
        assertThat(demarcheDTO1).isEqualTo(demarcheDTO2);
        demarcheDTO2.setId(2L);
        assertThat(demarcheDTO1).isNotEqualTo(demarcheDTO2);
        demarcheDTO1.setId(null);
        assertThat(demarcheDTO1).isNotEqualTo(demarcheDTO2);
    }
}

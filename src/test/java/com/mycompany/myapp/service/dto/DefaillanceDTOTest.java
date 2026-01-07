package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DefaillanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefaillanceDTO.class);
        DefaillanceDTO defaillanceDTO1 = new DefaillanceDTO();
        defaillanceDTO1.setId(1L);
        DefaillanceDTO defaillanceDTO2 = new DefaillanceDTO();
        assertThat(defaillanceDTO1).isNotEqualTo(defaillanceDTO2);
        defaillanceDTO2.setId(defaillanceDTO1.getId());
        assertThat(defaillanceDTO1).isEqualTo(defaillanceDTO2);
        defaillanceDTO2.setId(2L);
        assertThat(defaillanceDTO1).isNotEqualTo(defaillanceDTO2);
        defaillanceDTO1.setId(null);
        assertThat(defaillanceDTO1).isNotEqualTo(defaillanceDTO2);
    }
}

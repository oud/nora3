package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatutDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatutDTO.class);
        StatutDTO statutDTO1 = new StatutDTO();
        statutDTO1.setId(1L);
        StatutDTO statutDTO2 = new StatutDTO();
        assertThat(statutDTO1).isNotEqualTo(statutDTO2);
        statutDTO2.setId(statutDTO1.getId());
        assertThat(statutDTO1).isEqualTo(statutDTO2);
        statutDTO2.setId(2L);
        assertThat(statutDTO1).isNotEqualTo(statutDTO2);
        statutDTO1.setId(null);
        assertThat(statutDTO1).isNotEqualTo(statutDTO2);
    }
}

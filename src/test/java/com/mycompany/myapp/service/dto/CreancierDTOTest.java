package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreancierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreancierDTO.class);
        CreancierDTO creancierDTO1 = new CreancierDTO();
        creancierDTO1.setId(1L);
        CreancierDTO creancierDTO2 = new CreancierDTO();
        assertThat(creancierDTO1).isNotEqualTo(creancierDTO2);
        creancierDTO2.setId(creancierDTO1.getId());
        assertThat(creancierDTO1).isEqualTo(creancierDTO2);
        creancierDTO2.setId(2L);
        assertThat(creancierDTO1).isNotEqualTo(creancierDTO2);
        creancierDTO1.setId(null);
        assertThat(creancierDTO1).isNotEqualTo(creancierDTO2);
    }
}

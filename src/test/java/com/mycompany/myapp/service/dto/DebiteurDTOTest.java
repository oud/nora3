package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebiteurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebiteurDTO.class);
        DebiteurDTO debiteurDTO1 = new DebiteurDTO();
        debiteurDTO1.setId(1L);
        DebiteurDTO debiteurDTO2 = new DebiteurDTO();
        assertThat(debiteurDTO1).isNotEqualTo(debiteurDTO2);
        debiteurDTO2.setId(debiteurDTO1.getId());
        assertThat(debiteurDTO1).isEqualTo(debiteurDTO2);
        debiteurDTO2.setId(2L);
        assertThat(debiteurDTO1).isNotEqualTo(debiteurDTO2);
        debiteurDTO1.setId(null);
        assertThat(debiteurDTO1).isNotEqualTo(debiteurDTO2);
    }
}

package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CreancierTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreancierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Creancier.class);
        Creancier creancier1 = getCreancierSample1();
        Creancier creancier2 = new Creancier();
        assertThat(creancier1).isNotEqualTo(creancier2);

        creancier2.setId(creancier1.getId());
        assertThat(creancier1).isEqualTo(creancier2);

        creancier2 = getCreancierSample2();
        assertThat(creancier1).isNotEqualTo(creancier2);
    }

    @Test
    void dossierTest() {
        Creancier creancier = getCreancierRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        creancier.setDossier(dossierBack);
        assertThat(creancier.getDossier()).isEqualTo(dossierBack);
        assertThat(dossierBack.getCreancier()).isEqualTo(creancier);

        creancier.dossier(null);
        assertThat(creancier.getDossier()).isNull();
        assertThat(dossierBack.getCreancier()).isNull();
    }
}

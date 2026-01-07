package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.StatutTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statut.class);
        Statut statut1 = getStatutSample1();
        Statut statut2 = new Statut();
        assertThat(statut1).isNotEqualTo(statut2);

        statut2.setId(statut1.getId());
        assertThat(statut1).isEqualTo(statut2);

        statut2 = getStatutSample2();
        assertThat(statut1).isNotEqualTo(statut2);
    }

    @Test
    void dossierTest() {
        Statut statut = getStatutRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        statut.setDossier(dossierBack);
        assertThat(statut.getDossier()).isEqualTo(dossierBack);

        statut.dossier(null);
        assertThat(statut.getDossier()).isNull();
    }
}

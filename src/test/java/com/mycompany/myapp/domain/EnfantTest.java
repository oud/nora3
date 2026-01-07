package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EnfantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnfantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enfant.class);
        Enfant enfant1 = getEnfantSample1();
        Enfant enfant2 = new Enfant();
        assertThat(enfant1).isNotEqualTo(enfant2);

        enfant2.setId(enfant1.getId());
        assertThat(enfant1).isEqualTo(enfant2);

        enfant2 = getEnfantSample2();
        assertThat(enfant1).isNotEqualTo(enfant2);
    }

    @Test
    void dossierTest() {
        Enfant enfant = getEnfantRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        enfant.setDossier(dossierBack);
        assertThat(enfant.getDossier()).isEqualTo(dossierBack);

        enfant.dossier(null);
        assertThat(enfant.getDossier()).isNull();
    }
}

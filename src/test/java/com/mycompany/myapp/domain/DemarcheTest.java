package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemarcheTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemarcheTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demarche.class);
        Demarche demarche1 = getDemarcheSample1();
        Demarche demarche2 = new Demarche();
        assertThat(demarche1).isNotEqualTo(demarche2);

        demarche2.setId(demarche1.getId());
        assertThat(demarche1).isEqualTo(demarche2);

        demarche2 = getDemarcheSample2();
        assertThat(demarche1).isNotEqualTo(demarche2);
    }

    @Test
    void dossierTest() {
        Demarche demarche = getDemarcheRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        demarche.setDossier(dossierBack);
        assertThat(demarche.getDossier()).isEqualTo(dossierBack);

        demarche.dossier(null);
        assertThat(demarche.getDossier()).isNull();
    }
}

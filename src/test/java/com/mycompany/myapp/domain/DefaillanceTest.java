package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DefaillanceTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DefaillanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Defaillance.class);
        Defaillance defaillance1 = getDefaillanceSample1();
        Defaillance defaillance2 = new Defaillance();
        assertThat(defaillance1).isNotEqualTo(defaillance2);

        defaillance2.setId(defaillance1.getId());
        assertThat(defaillance1).isEqualTo(defaillance2);

        defaillance2 = getDefaillanceSample2();
        assertThat(defaillance1).isNotEqualTo(defaillance2);
    }

    @Test
    void dossierTest() {
        Defaillance defaillance = getDefaillanceRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        defaillance.setDossier(dossierBack);
        assertThat(defaillance.getDossier()).isEqualTo(dossierBack);

        defaillance.dossier(null);
        assertThat(defaillance.getDossier()).isNull();
    }
}

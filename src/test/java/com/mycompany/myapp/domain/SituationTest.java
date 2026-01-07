package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DebiteurTestSamples.*;
import static com.mycompany.myapp.domain.SituationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SituationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Situation.class);
        Situation situation1 = getSituationSample1();
        Situation situation2 = new Situation();
        assertThat(situation1).isNotEqualTo(situation2);

        situation2.setId(situation1.getId());
        assertThat(situation1).isEqualTo(situation2);

        situation2 = getSituationSample2();
        assertThat(situation1).isNotEqualTo(situation2);
    }

    @Test
    void debiteurTest() {
        Situation situation = getSituationRandomSampleGenerator();
        Debiteur debiteurBack = getDebiteurRandomSampleGenerator();

        situation.setDebiteur(debiteurBack);
        assertThat(situation.getDebiteur()).isEqualTo(debiteurBack);

        situation.debiteur(null);
        assertThat(situation.getDebiteur()).isNull();
    }
}

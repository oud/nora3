package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.RechercheSolvableTestSamples.*;
import static com.mycompany.myapp.domain.ResultatSolvableTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultatSolvableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatSolvable.class);
        ResultatSolvable resultatSolvable1 = getResultatSolvableSample1();
        ResultatSolvable resultatSolvable2 = new ResultatSolvable();
        assertThat(resultatSolvable1).isNotEqualTo(resultatSolvable2);

        resultatSolvable2.setId(resultatSolvable1.getId());
        assertThat(resultatSolvable1).isEqualTo(resultatSolvable2);

        resultatSolvable2 = getResultatSolvableSample2();
        assertThat(resultatSolvable1).isNotEqualTo(resultatSolvable2);
    }

    @Test
    void rechercheSolvableTest() {
        ResultatSolvable resultatSolvable = getResultatSolvableRandomSampleGenerator();
        RechercheSolvable rechercheSolvableBack = getRechercheSolvableRandomSampleGenerator();

        resultatSolvable.setRechercheSolvable(rechercheSolvableBack);
        assertThat(resultatSolvable.getRechercheSolvable()).isEqualTo(rechercheSolvableBack);

        resultatSolvable.rechercheSolvable(null);
        assertThat(resultatSolvable.getRechercheSolvable()).isNull();
    }
}

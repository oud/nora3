package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DebiteurTestSamples.*;
import static com.mycompany.myapp.domain.RechercheSolvableTestSamples.*;
import static com.mycompany.myapp.domain.ResultatSolvableTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RechercheSolvableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RechercheSolvable.class);
        RechercheSolvable rechercheSolvable1 = getRechercheSolvableSample1();
        RechercheSolvable rechercheSolvable2 = new RechercheSolvable();
        assertThat(rechercheSolvable1).isNotEqualTo(rechercheSolvable2);

        rechercheSolvable2.setId(rechercheSolvable1.getId());
        assertThat(rechercheSolvable1).isEqualTo(rechercheSolvable2);

        rechercheSolvable2 = getRechercheSolvableSample2();
        assertThat(rechercheSolvable1).isNotEqualTo(rechercheSolvable2);
    }

    @Test
    void resultatTest() {
        RechercheSolvable rechercheSolvable = getRechercheSolvableRandomSampleGenerator();
        ResultatSolvable resultatSolvableBack = getResultatSolvableRandomSampleGenerator();

        rechercheSolvable.addResultat(resultatSolvableBack);
        assertThat(rechercheSolvable.getResultats()).containsOnly(resultatSolvableBack);
        assertThat(resultatSolvableBack.getRechercheSolvable()).isEqualTo(rechercheSolvable);

        rechercheSolvable.removeResultat(resultatSolvableBack);
        assertThat(rechercheSolvable.getResultats()).doesNotContain(resultatSolvableBack);
        assertThat(resultatSolvableBack.getRechercheSolvable()).isNull();

        rechercheSolvable.resultats(new HashSet<>(Set.of(resultatSolvableBack)));
        assertThat(rechercheSolvable.getResultats()).containsOnly(resultatSolvableBack);
        assertThat(resultatSolvableBack.getRechercheSolvable()).isEqualTo(rechercheSolvable);

        rechercheSolvable.setResultats(new HashSet<>());
        assertThat(rechercheSolvable.getResultats()).doesNotContain(resultatSolvableBack);
        assertThat(resultatSolvableBack.getRechercheSolvable()).isNull();
    }

    @Test
    void debiteurTest() {
        RechercheSolvable rechercheSolvable = getRechercheSolvableRandomSampleGenerator();
        Debiteur debiteurBack = getDebiteurRandomSampleGenerator();

        rechercheSolvable.setDebiteur(debiteurBack);
        assertThat(rechercheSolvable.getDebiteur()).isEqualTo(debiteurBack);

        rechercheSolvable.debiteur(null);
        assertThat(rechercheSolvable.getDebiteur()).isNull();
    }
}

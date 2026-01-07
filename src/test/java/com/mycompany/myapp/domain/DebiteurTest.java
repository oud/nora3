package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DebiteurTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.RechercheSolvableTestSamples.*;
import static com.mycompany.myapp.domain.SituationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DebiteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debiteur.class);
        Debiteur debiteur1 = getDebiteurSample1();
        Debiteur debiteur2 = new Debiteur();
        assertThat(debiteur1).isNotEqualTo(debiteur2);

        debiteur2.setId(debiteur1.getId());
        assertThat(debiteur1).isEqualTo(debiteur2);

        debiteur2 = getDebiteurSample2();
        assertThat(debiteur1).isNotEqualTo(debiteur2);
    }

    @Test
    void situationTest() {
        Debiteur debiteur = getDebiteurRandomSampleGenerator();
        Situation situationBack = getSituationRandomSampleGenerator();

        debiteur.addSituation(situationBack);
        assertThat(debiteur.getSituations()).containsOnly(situationBack);
        assertThat(situationBack.getDebiteur()).isEqualTo(debiteur);

        debiteur.removeSituation(situationBack);
        assertThat(debiteur.getSituations()).doesNotContain(situationBack);
        assertThat(situationBack.getDebiteur()).isNull();

        debiteur.situations(new HashSet<>(Set.of(situationBack)));
        assertThat(debiteur.getSituations()).containsOnly(situationBack);
        assertThat(situationBack.getDebiteur()).isEqualTo(debiteur);

        debiteur.setSituations(new HashSet<>());
        assertThat(debiteur.getSituations()).doesNotContain(situationBack);
        assertThat(situationBack.getDebiteur()).isNull();
    }

    @Test
    void rechercheTest() {
        Debiteur debiteur = getDebiteurRandomSampleGenerator();
        RechercheSolvable rechercheSolvableBack = getRechercheSolvableRandomSampleGenerator();

        debiteur.addRecherche(rechercheSolvableBack);
        assertThat(debiteur.getRecherches()).containsOnly(rechercheSolvableBack);
        assertThat(rechercheSolvableBack.getDebiteur()).isEqualTo(debiteur);

        debiteur.removeRecherche(rechercheSolvableBack);
        assertThat(debiteur.getRecherches()).doesNotContain(rechercheSolvableBack);
        assertThat(rechercheSolvableBack.getDebiteur()).isNull();

        debiteur.recherches(new HashSet<>(Set.of(rechercheSolvableBack)));
        assertThat(debiteur.getRecherches()).containsOnly(rechercheSolvableBack);
        assertThat(rechercheSolvableBack.getDebiteur()).isEqualTo(debiteur);

        debiteur.setRecherches(new HashSet<>());
        assertThat(debiteur.getRecherches()).doesNotContain(rechercheSolvableBack);
        assertThat(rechercheSolvableBack.getDebiteur()).isNull();
    }

    @Test
    void dossierTest() {
        Debiteur debiteur = getDebiteurRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        debiteur.setDossier(dossierBack);
        assertThat(debiteur.getDossier()).isEqualTo(dossierBack);
        assertThat(dossierBack.getDebiteur()).isEqualTo(debiteur);

        debiteur.dossier(null);
        assertThat(debiteur.getDossier()).isNull();
        assertThat(dossierBack.getDebiteur()).isNull();
    }
}

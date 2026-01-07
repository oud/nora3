package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CreancierTestSamples.*;
import static com.mycompany.myapp.domain.DebiteurTestSamples.*;
import static com.mycompany.myapp.domain.DefaillanceTestSamples.*;
import static com.mycompany.myapp.domain.DemarcheTestSamples.*;
import static com.mycompany.myapp.domain.DossierTestSamples.*;
import static com.mycompany.myapp.domain.EnfantTestSamples.*;
import static com.mycompany.myapp.domain.StatutTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dossier.class);
        Dossier dossier1 = getDossierSample1();
        Dossier dossier2 = new Dossier();
        assertThat(dossier1).isNotEqualTo(dossier2);

        dossier2.setId(dossier1.getId());
        assertThat(dossier1).isEqualTo(dossier2);

        dossier2 = getDossierSample2();
        assertThat(dossier1).isNotEqualTo(dossier2);
    }

    @Test
    void debiteurTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Debiteur debiteurBack = getDebiteurRandomSampleGenerator();

        dossier.setDebiteur(debiteurBack);
        assertThat(dossier.getDebiteur()).isEqualTo(debiteurBack);

        dossier.debiteur(null);
        assertThat(dossier.getDebiteur()).isNull();
    }

    @Test
    void creancierTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Creancier creancierBack = getCreancierRandomSampleGenerator();

        dossier.setCreancier(creancierBack);
        assertThat(dossier.getCreancier()).isEqualTo(creancierBack);

        dossier.creancier(null);
        assertThat(dossier.getCreancier()).isNull();
    }

    @Test
    void statutTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Statut statutBack = getStatutRandomSampleGenerator();

        dossier.addStatut(statutBack);
        assertThat(dossier.getStatuts()).containsOnly(statutBack);
        assertThat(statutBack.getDossier()).isEqualTo(dossier);

        dossier.removeStatut(statutBack);
        assertThat(dossier.getStatuts()).doesNotContain(statutBack);
        assertThat(statutBack.getDossier()).isNull();

        dossier.statuts(new HashSet<>(Set.of(statutBack)));
        assertThat(dossier.getStatuts()).containsOnly(statutBack);
        assertThat(statutBack.getDossier()).isEqualTo(dossier);

        dossier.setStatuts(new HashSet<>());
        assertThat(dossier.getStatuts()).doesNotContain(statutBack);
        assertThat(statutBack.getDossier()).isNull();
    }

    @Test
    void enfantTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Enfant enfantBack = getEnfantRandomSampleGenerator();

        dossier.addEnfant(enfantBack);
        assertThat(dossier.getEnfants()).containsOnly(enfantBack);
        assertThat(enfantBack.getDossier()).isEqualTo(dossier);

        dossier.removeEnfant(enfantBack);
        assertThat(dossier.getEnfants()).doesNotContain(enfantBack);
        assertThat(enfantBack.getDossier()).isNull();

        dossier.enfants(new HashSet<>(Set.of(enfantBack)));
        assertThat(dossier.getEnfants()).containsOnly(enfantBack);
        assertThat(enfantBack.getDossier()).isEqualTo(dossier);

        dossier.setEnfants(new HashSet<>());
        assertThat(dossier.getEnfants()).doesNotContain(enfantBack);
        assertThat(enfantBack.getDossier()).isNull();
    }

    @Test
    void demarcheTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Demarche demarcheBack = getDemarcheRandomSampleGenerator();

        dossier.addDemarche(demarcheBack);
        assertThat(dossier.getDemarches()).containsOnly(demarcheBack);
        assertThat(demarcheBack.getDossier()).isEqualTo(dossier);

        dossier.removeDemarche(demarcheBack);
        assertThat(dossier.getDemarches()).doesNotContain(demarcheBack);
        assertThat(demarcheBack.getDossier()).isNull();

        dossier.demarches(new HashSet<>(Set.of(demarcheBack)));
        assertThat(dossier.getDemarches()).containsOnly(demarcheBack);
        assertThat(demarcheBack.getDossier()).isEqualTo(dossier);

        dossier.setDemarches(new HashSet<>());
        assertThat(dossier.getDemarches()).doesNotContain(demarcheBack);
        assertThat(demarcheBack.getDossier()).isNull();
    }

    @Test
    void defaillanceTest() {
        Dossier dossier = getDossierRandomSampleGenerator();
        Defaillance defaillanceBack = getDefaillanceRandomSampleGenerator();

        dossier.addDefaillance(defaillanceBack);
        assertThat(dossier.getDefaillances()).containsOnly(defaillanceBack);
        assertThat(defaillanceBack.getDossier()).isEqualTo(dossier);

        dossier.removeDefaillance(defaillanceBack);
        assertThat(dossier.getDefaillances()).doesNotContain(defaillanceBack);
        assertThat(defaillanceBack.getDossier()).isNull();

        dossier.defaillances(new HashSet<>(Set.of(defaillanceBack)));
        assertThat(dossier.getDefaillances()).containsOnly(defaillanceBack);
        assertThat(defaillanceBack.getDossier()).isEqualTo(dossier);

        dossier.setDefaillances(new HashSet<>());
        assertThat(dossier.getDefaillances()).doesNotContain(defaillanceBack);
        assertThat(defaillanceBack.getDossier()).isNull();
    }
}

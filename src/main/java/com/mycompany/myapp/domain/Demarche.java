package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demarche.
 */
@Entity
@Table(name = "demarche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Demarche implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "demarche_date")
    private LocalDate demarcheDate;

    @Column(name = "num_demarche")
    private String numDemarche;

    @Column(name = "code_origine")
    private String codeOrigine;

    @Column(name = "code_statut")
    private String codeStatut;

    @Column(name = "code_agent")
    private String codeAgent;

    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "creation_date")
    private LocalTime creationDate;

    @Column(name = "user_maj")
    private String userMaj;

    @Column(name = "maj_date")
    private LocalTime majDate;

    @Column(name = "num_maj")
    private Integer numMaj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "debiteur", "creancier", "statuts", "enfants", "demarches", "defaillances" }, allowSetters = true)
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demarche id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDemarcheDate() {
        return this.demarcheDate;
    }

    public Demarche demarcheDate(LocalDate demarcheDate) {
        this.setDemarcheDate(demarcheDate);
        return this;
    }

    public void setDemarcheDate(LocalDate demarcheDate) {
        this.demarcheDate = demarcheDate;
    }

    public String getNumDemarche() {
        return this.numDemarche;
    }

    public Demarche numDemarche(String numDemarche) {
        this.setNumDemarche(numDemarche);
        return this;
    }

    public void setNumDemarche(String numDemarche) {
        this.numDemarche = numDemarche;
    }

    public String getCodeOrigine() {
        return this.codeOrigine;
    }

    public Demarche codeOrigine(String codeOrigine) {
        this.setCodeOrigine(codeOrigine);
        return this;
    }

    public void setCodeOrigine(String codeOrigine) {
        this.codeOrigine = codeOrigine;
    }

    public String getCodeStatut() {
        return this.codeStatut;
    }

    public Demarche codeStatut(String codeStatut) {
        this.setCodeStatut(codeStatut);
        return this;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Demarche codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Demarche userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Demarche creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Demarche userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Demarche majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Demarche numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Demarche dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demarche)) {
            return false;
        }
        return getId() != null && getId().equals(((Demarche) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demarche{" +
            "id=" + getId() +
            ", demarcheDate='" + getDemarcheDate() + "'" +
            ", numDemarche='" + getNumDemarche() + "'" +
            ", codeOrigine='" + getCodeOrigine() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

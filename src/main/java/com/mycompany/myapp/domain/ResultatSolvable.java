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
 * A ResultatSolvable.
 */
@Entity
@Table(name = "resultat_solvable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultatSolvable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mois_solvabilite_date")
    private LocalDate moisSolvabiliteDate;

    @Column(name = "code_etat_solvabilite")
    private String codeEtatSolvabilite;

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
    @JsonIgnoreProperties(value = { "resultats", "debiteur" }, allowSetters = true)
    private RechercheSolvable rechercheSolvable;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResultatSolvable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMoisSolvabiliteDate() {
        return this.moisSolvabiliteDate;
    }

    public ResultatSolvable moisSolvabiliteDate(LocalDate moisSolvabiliteDate) {
        this.setMoisSolvabiliteDate(moisSolvabiliteDate);
        return this;
    }

    public void setMoisSolvabiliteDate(LocalDate moisSolvabiliteDate) {
        this.moisSolvabiliteDate = moisSolvabiliteDate;
    }

    public String getCodeEtatSolvabilite() {
        return this.codeEtatSolvabilite;
    }

    public ResultatSolvable codeEtatSolvabilite(String codeEtatSolvabilite) {
        this.setCodeEtatSolvabilite(codeEtatSolvabilite);
        return this;
    }

    public void setCodeEtatSolvabilite(String codeEtatSolvabilite) {
        this.codeEtatSolvabilite = codeEtatSolvabilite;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public ResultatSolvable codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public ResultatSolvable userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public ResultatSolvable creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public ResultatSolvable userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public ResultatSolvable majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public ResultatSolvable numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public RechercheSolvable getRechercheSolvable() {
        return this.rechercheSolvable;
    }

    public void setRechercheSolvable(RechercheSolvable rechercheSolvable) {
        this.rechercheSolvable = rechercheSolvable;
    }

    public ResultatSolvable rechercheSolvable(RechercheSolvable rechercheSolvable) {
        this.setRechercheSolvable(rechercheSolvable);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultatSolvable)) {
            return false;
        }
        return getId() != null && getId().equals(((ResultatSolvable) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatSolvable{" +
            "id=" + getId() +
            ", moisSolvabiliteDate='" + getMoisSolvabiliteDate() + "'" +
            ", codeEtatSolvabilite='" + getCodeEtatSolvabilite() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

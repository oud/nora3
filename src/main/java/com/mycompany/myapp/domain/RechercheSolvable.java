package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RechercheSolvable.
 */
@Entity
@Table(name = "recherche_solvable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RechercheSolvable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "recherche_solvabilite_debut_date")
    private LocalTime rechercheSolvabiliteDebutDate;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rechercheSolvable")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rechercheSolvable" }, allowSetters = true)
    private Set<ResultatSolvable> resultats = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "situations", "recherches", "dossier" }, allowSetters = true)
    private Debiteur debiteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RechercheSolvable id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getRechercheSolvabiliteDebutDate() {
        return this.rechercheSolvabiliteDebutDate;
    }

    public RechercheSolvable rechercheSolvabiliteDebutDate(LocalTime rechercheSolvabiliteDebutDate) {
        this.setRechercheSolvabiliteDebutDate(rechercheSolvabiliteDebutDate);
        return this;
    }

    public void setRechercheSolvabiliteDebutDate(LocalTime rechercheSolvabiliteDebutDate) {
        this.rechercheSolvabiliteDebutDate = rechercheSolvabiliteDebutDate;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public RechercheSolvable codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public RechercheSolvable userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public RechercheSolvable creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public RechercheSolvable userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public RechercheSolvable majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public RechercheSolvable numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Set<ResultatSolvable> getResultats() {
        return this.resultats;
    }

    public void setResultats(Set<ResultatSolvable> resultatSolvables) {
        if (this.resultats != null) {
            this.resultats.forEach(i -> i.setRechercheSolvable(null));
        }
        if (resultatSolvables != null) {
            resultatSolvables.forEach(i -> i.setRechercheSolvable(this));
        }
        this.resultats = resultatSolvables;
    }

    public RechercheSolvable resultats(Set<ResultatSolvable> resultatSolvables) {
        this.setResultats(resultatSolvables);
        return this;
    }

    public RechercheSolvable addResultat(ResultatSolvable resultatSolvable) {
        this.resultats.add(resultatSolvable);
        resultatSolvable.setRechercheSolvable(this);
        return this;
    }

    public RechercheSolvable removeResultat(ResultatSolvable resultatSolvable) {
        this.resultats.remove(resultatSolvable);
        resultatSolvable.setRechercheSolvable(null);
        return this;
    }

    public Debiteur getDebiteur() {
        return this.debiteur;
    }

    public void setDebiteur(Debiteur debiteur) {
        this.debiteur = debiteur;
    }

    public RechercheSolvable debiteur(Debiteur debiteur) {
        this.setDebiteur(debiteur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RechercheSolvable)) {
            return false;
        }
        return getId() != null && getId().equals(((RechercheSolvable) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RechercheSolvable{" +
            "id=" + getId() +
            ", rechercheSolvabiliteDebutDate='" + getRechercheSolvabiliteDebutDate() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

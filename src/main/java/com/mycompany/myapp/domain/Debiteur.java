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
 * A Debiteur.
 */
@Entity
@Table(name = "debiteur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Debiteur implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nir")
    private String nir;

    @Column(name = "cle_nir")
    private Integer cleNir;

    @Column(name = "num_alloc_cristal")
    private Integer numAllocCristal;

    @Column(name = "code_organisme_cristal")
    private String codeOrganismeCristal;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "debiteur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "debiteur" }, allowSetters = true)
    private Set<Situation> situations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "debiteur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resultats", "debiteur" }, allowSetters = true)
    private Set<RechercheSolvable> recherches = new HashSet<>();

    @JsonIgnoreProperties(value = { "debiteur", "creancier", "statuts", "enfants", "demarches", "defaillances" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "debiteur")
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Debiteur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNir() {
        return this.nir;
    }

    public Debiteur nir(String nir) {
        this.setNir(nir);
        return this;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public Integer getCleNir() {
        return this.cleNir;
    }

    public Debiteur cleNir(Integer cleNir) {
        this.setCleNir(cleNir);
        return this;
    }

    public void setCleNir(Integer cleNir) {
        this.cleNir = cleNir;
    }

    public Integer getNumAllocCristal() {
        return this.numAllocCristal;
    }

    public Debiteur numAllocCristal(Integer numAllocCristal) {
        this.setNumAllocCristal(numAllocCristal);
        return this;
    }

    public void setNumAllocCristal(Integer numAllocCristal) {
        this.numAllocCristal = numAllocCristal;
    }

    public String getCodeOrganismeCristal() {
        return this.codeOrganismeCristal;
    }

    public Debiteur codeOrganismeCristal(String codeOrganismeCristal) {
        this.setCodeOrganismeCristal(codeOrganismeCristal);
        return this;
    }

    public void setCodeOrganismeCristal(String codeOrganismeCristal) {
        this.codeOrganismeCristal = codeOrganismeCristal;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Debiteur codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Debiteur userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Debiteur creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Debiteur userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Debiteur majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Debiteur numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Set<Situation> getSituations() {
        return this.situations;
    }

    public void setSituations(Set<Situation> situations) {
        if (this.situations != null) {
            this.situations.forEach(i -> i.setDebiteur(null));
        }
        if (situations != null) {
            situations.forEach(i -> i.setDebiteur(this));
        }
        this.situations = situations;
    }

    public Debiteur situations(Set<Situation> situations) {
        this.setSituations(situations);
        return this;
    }

    public Debiteur addSituation(Situation situation) {
        this.situations.add(situation);
        situation.setDebiteur(this);
        return this;
    }

    public Debiteur removeSituation(Situation situation) {
        this.situations.remove(situation);
        situation.setDebiteur(null);
        return this;
    }

    public Set<RechercheSolvable> getRecherches() {
        return this.recherches;
    }

    public void setRecherches(Set<RechercheSolvable> rechercheSolvables) {
        if (this.recherches != null) {
            this.recherches.forEach(i -> i.setDebiteur(null));
        }
        if (rechercheSolvables != null) {
            rechercheSolvables.forEach(i -> i.setDebiteur(this));
        }
        this.recherches = rechercheSolvables;
    }

    public Debiteur recherches(Set<RechercheSolvable> rechercheSolvables) {
        this.setRecherches(rechercheSolvables);
        return this;
    }

    public Debiteur addRecherche(RechercheSolvable rechercheSolvable) {
        this.recherches.add(rechercheSolvable);
        rechercheSolvable.setDebiteur(this);
        return this;
    }

    public Debiteur removeRecherche(RechercheSolvable rechercheSolvable) {
        this.recherches.remove(rechercheSolvable);
        rechercheSolvable.setDebiteur(null);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        if (this.dossier != null) {
            this.dossier.setDebiteur(null);
        }
        if (dossier != null) {
            dossier.setDebiteur(this);
        }
        this.dossier = dossier;
    }

    public Debiteur dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Debiteur)) {
            return false;
        }
        return getId() != null && getId().equals(((Debiteur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Debiteur{" +
            "id=" + getId() +
            ", nir='" + getNir() + "'" +
            ", cleNir=" + getCleNir() +
            ", numAllocCristal=" + getNumAllocCristal() +
            ", codeOrganismeCristal='" + getCodeOrganismeCristal() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

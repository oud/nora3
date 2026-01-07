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
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dossier implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "num_dossier_nor")
    private String numDossierNor;

    @Column(name = "num_dossier_gaia")
    private String numDossierGaia;

    @Column(name = "reception_date_nor")
    private LocalTime receptionDateNor;

    @Column(name = "validation_date_nor")
    private LocalTime validationDateNor;

    @Column(name = "code_organisme")
    private String codeOrganisme;

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

    @JsonIgnoreProperties(value = { "situations", "recherches", "dossier" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Debiteur debiteur;

    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Creancier creancier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Statut> statuts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Enfant> enfants = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Demarche> demarches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Defaillance> defaillances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDossierNor() {
        return this.numDossierNor;
    }

    public Dossier numDossierNor(String numDossierNor) {
        this.setNumDossierNor(numDossierNor);
        return this;
    }

    public void setNumDossierNor(String numDossierNor) {
        this.numDossierNor = numDossierNor;
    }

    public String getNumDossierGaia() {
        return this.numDossierGaia;
    }

    public Dossier numDossierGaia(String numDossierGaia) {
        this.setNumDossierGaia(numDossierGaia);
        return this;
    }

    public void setNumDossierGaia(String numDossierGaia) {
        this.numDossierGaia = numDossierGaia;
    }

    public LocalTime getReceptionDateNor() {
        return this.receptionDateNor;
    }

    public Dossier receptionDateNor(LocalTime receptionDateNor) {
        this.setReceptionDateNor(receptionDateNor);
        return this;
    }

    public void setReceptionDateNor(LocalTime receptionDateNor) {
        this.receptionDateNor = receptionDateNor;
    }

    public LocalTime getValidationDateNor() {
        return this.validationDateNor;
    }

    public Dossier validationDateNor(LocalTime validationDateNor) {
        this.setValidationDateNor(validationDateNor);
        return this;
    }

    public void setValidationDateNor(LocalTime validationDateNor) {
        this.validationDateNor = validationDateNor;
    }

    public String getCodeOrganisme() {
        return this.codeOrganisme;
    }

    public Dossier codeOrganisme(String codeOrganisme) {
        this.setCodeOrganisme(codeOrganisme);
        return this;
    }

    public void setCodeOrganisme(String codeOrganisme) {
        this.codeOrganisme = codeOrganisme;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Dossier codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Dossier userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Dossier creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Dossier userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Dossier majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Dossier numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Debiteur getDebiteur() {
        return this.debiteur;
    }

    public void setDebiteur(Debiteur debiteur) {
        this.debiteur = debiteur;
    }

    public Dossier debiteur(Debiteur debiteur) {
        this.setDebiteur(debiteur);
        return this;
    }

    public Creancier getCreancier() {
        return this.creancier;
    }

    public void setCreancier(Creancier creancier) {
        this.creancier = creancier;
    }

    public Dossier creancier(Creancier creancier) {
        this.setCreancier(creancier);
        return this;
    }

    public Set<Statut> getStatuts() {
        return this.statuts;
    }

    public void setStatuts(Set<Statut> statuts) {
        if (this.statuts != null) {
            this.statuts.forEach(i -> i.setDossier(null));
        }
        if (statuts != null) {
            statuts.forEach(i -> i.setDossier(this));
        }
        this.statuts = statuts;
    }

    public Dossier statuts(Set<Statut> statuts) {
        this.setStatuts(statuts);
        return this;
    }

    public Dossier addStatut(Statut statut) {
        this.statuts.add(statut);
        statut.setDossier(this);
        return this;
    }

    public Dossier removeStatut(Statut statut) {
        this.statuts.remove(statut);
        statut.setDossier(null);
        return this;
    }

    public Set<Enfant> getEnfants() {
        return this.enfants;
    }

    public void setEnfants(Set<Enfant> enfants) {
        if (this.enfants != null) {
            this.enfants.forEach(i -> i.setDossier(null));
        }
        if (enfants != null) {
            enfants.forEach(i -> i.setDossier(this));
        }
        this.enfants = enfants;
    }

    public Dossier enfants(Set<Enfant> enfants) {
        this.setEnfants(enfants);
        return this;
    }

    public Dossier addEnfant(Enfant enfant) {
        this.enfants.add(enfant);
        enfant.setDossier(this);
        return this;
    }

    public Dossier removeEnfant(Enfant enfant) {
        this.enfants.remove(enfant);
        enfant.setDossier(null);
        return this;
    }

    public Set<Demarche> getDemarches() {
        return this.demarches;
    }

    public void setDemarches(Set<Demarche> demarches) {
        if (this.demarches != null) {
            this.demarches.forEach(i -> i.setDossier(null));
        }
        if (demarches != null) {
            demarches.forEach(i -> i.setDossier(this));
        }
        this.demarches = demarches;
    }

    public Dossier demarches(Set<Demarche> demarches) {
        this.setDemarches(demarches);
        return this;
    }

    public Dossier addDemarche(Demarche demarche) {
        this.demarches.add(demarche);
        demarche.setDossier(this);
        return this;
    }

    public Dossier removeDemarche(Demarche demarche) {
        this.demarches.remove(demarche);
        demarche.setDossier(null);
        return this;
    }

    public Set<Defaillance> getDefaillances() {
        return this.defaillances;
    }

    public void setDefaillances(Set<Defaillance> defaillances) {
        if (this.defaillances != null) {
            this.defaillances.forEach(i -> i.setDossier(null));
        }
        if (defaillances != null) {
            defaillances.forEach(i -> i.setDossier(this));
        }
        this.defaillances = defaillances;
    }

    public Dossier defaillances(Set<Defaillance> defaillances) {
        this.setDefaillances(defaillances);
        return this;
    }

    public Dossier addDefaillance(Defaillance defaillance) {
        this.defaillances.add(defaillance);
        defaillance.setDossier(this);
        return this;
    }

    public Dossier removeDefaillance(Defaillance defaillance) {
        this.defaillances.remove(defaillance);
        defaillance.setDossier(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return getId() != null && getId().equals(((Dossier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", numDossierNor='" + getNumDossierNor() + "'" +
            ", numDossierGaia='" + getNumDossierGaia() + "'" +
            ", receptionDateNor='" + getReceptionDateNor() + "'" +
            ", validationDateNor='" + getValidationDateNor() + "'" +
            ", codeOrganisme='" + getCodeOrganisme() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

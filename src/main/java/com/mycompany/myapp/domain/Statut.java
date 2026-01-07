package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Statut.
 */
@Entity
@Table(name = "statut")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Statut implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "statut_debut_date")
    private LocalTime statutDebutDate;

    @Column(name = "statut_fin_date")
    private LocalTime statutFinDate;

    @Column(name = "code_statut")
    private String codeStatut;

    @Column(name = "motif_statut")
    private String motifStatut;

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

    public Statut id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStatutDebutDate() {
        return this.statutDebutDate;
    }

    public Statut statutDebutDate(LocalTime statutDebutDate) {
        this.setStatutDebutDate(statutDebutDate);
        return this;
    }

    public void setStatutDebutDate(LocalTime statutDebutDate) {
        this.statutDebutDate = statutDebutDate;
    }

    public LocalTime getStatutFinDate() {
        return this.statutFinDate;
    }

    public Statut statutFinDate(LocalTime statutFinDate) {
        this.setStatutFinDate(statutFinDate);
        return this;
    }

    public void setStatutFinDate(LocalTime statutFinDate) {
        this.statutFinDate = statutFinDate;
    }

    public String getCodeStatut() {
        return this.codeStatut;
    }

    public Statut codeStatut(String codeStatut) {
        this.setCodeStatut(codeStatut);
        return this;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getMotifStatut() {
        return this.motifStatut;
    }

    public Statut motifStatut(String motifStatut) {
        this.setMotifStatut(motifStatut);
        return this;
    }

    public void setMotifStatut(String motifStatut) {
        this.motifStatut = motifStatut;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Statut codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Statut userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Statut creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Statut userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Statut majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Statut numMaj(Integer numMaj) {
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

    public Statut dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statut)) {
            return false;
        }
        return getId() != null && getId().equals(((Statut) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statut{" +
            "id=" + getId() +
            ", statutDebutDate='" + getStatutDebutDate() + "'" +
            ", statutFinDate='" + getStatutFinDate() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", motifStatut='" + getMotifStatut() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

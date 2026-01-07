package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Defaillance.
 */
@Entity
@Table(name = "defaillance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Defaillance implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mois_defaillance")
    private LocalDate moisDefaillance;

    @Column(name = "montant_pa_due", precision = 21, scale = 2)
    private BigDecimal montantPADue;

    @Column(name = "montant_pa_versee", precision = 21, scale = 2)
    private BigDecimal montantPAVersee;

    @Column(name = "flag_dette_initiale")
    private Boolean flagDetteInitiale;

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

    public Defaillance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMoisDefaillance() {
        return this.moisDefaillance;
    }

    public Defaillance moisDefaillance(LocalDate moisDefaillance) {
        this.setMoisDefaillance(moisDefaillance);
        return this;
    }

    public void setMoisDefaillance(LocalDate moisDefaillance) {
        this.moisDefaillance = moisDefaillance;
    }

    public BigDecimal getMontantPADue() {
        return this.montantPADue;
    }

    public Defaillance montantPADue(BigDecimal montantPADue) {
        this.setMontantPADue(montantPADue);
        return this;
    }

    public void setMontantPADue(BigDecimal montantPADue) {
        this.montantPADue = montantPADue;
    }

    public BigDecimal getMontantPAVersee() {
        return this.montantPAVersee;
    }

    public Defaillance montantPAVersee(BigDecimal montantPAVersee) {
        this.setMontantPAVersee(montantPAVersee);
        return this;
    }

    public void setMontantPAVersee(BigDecimal montantPAVersee) {
        this.montantPAVersee = montantPAVersee;
    }

    public Boolean getFlagDetteInitiale() {
        return this.flagDetteInitiale;
    }

    public Defaillance flagDetteInitiale(Boolean flagDetteInitiale) {
        this.setFlagDetteInitiale(flagDetteInitiale);
        return this;
    }

    public void setFlagDetteInitiale(Boolean flagDetteInitiale) {
        this.flagDetteInitiale = flagDetteInitiale;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Defaillance codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Defaillance userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Defaillance creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Defaillance userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Defaillance majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Defaillance numMaj(Integer numMaj) {
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

    public Defaillance dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Defaillance)) {
            return false;
        }
        return getId() != null && getId().equals(((Defaillance) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Defaillance{" +
            "id=" + getId() +
            ", moisDefaillance='" + getMoisDefaillance() + "'" +
            ", montantPADue=" + getMontantPADue() +
            ", montantPAVersee=" + getMontantPAVersee() +
            ", flagDetteInitiale='" + getFlagDetteInitiale() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

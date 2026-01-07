package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Defaillance} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DefaillanceDTO implements Serializable {

    private Long id;

    private LocalDate moisDefaillance;

    private BigDecimal montantPADue;

    private BigDecimal montantPAVersee;

    private Boolean flagDetteInitiale;

    private String codeAgent;

    private String userCreation;

    private LocalTime creationDate;

    private String userMaj;

    private LocalTime majDate;

    private Integer numMaj;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMoisDefaillance() {
        return moisDefaillance;
    }

    public void setMoisDefaillance(LocalDate moisDefaillance) {
        this.moisDefaillance = moisDefaillance;
    }

    public BigDecimal getMontantPADue() {
        return montantPADue;
    }

    public void setMontantPADue(BigDecimal montantPADue) {
        this.montantPADue = montantPADue;
    }

    public BigDecimal getMontantPAVersee() {
        return montantPAVersee;
    }

    public void setMontantPAVersee(BigDecimal montantPAVersee) {
        this.montantPAVersee = montantPAVersee;
    }

    public Boolean getFlagDetteInitiale() {
        return flagDetteInitiale;
    }

    public void setFlagDetteInitiale(Boolean flagDetteInitiale) {
        this.flagDetteInitiale = flagDetteInitiale;
    }

    public String getCodeAgent() {
        return codeAgent;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return userMaj;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return majDate;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return numMaj;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public DossierDTO getDossier() {
        return dossier;
    }

    public void setDossier(DossierDTO dossier) {
        this.dossier = dossier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaillanceDTO)) {
            return false;
        }

        DefaillanceDTO defaillanceDTO = (DefaillanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, defaillanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaillanceDTO{" +
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
            ", dossier=" + getDossier() +
            "}";
    }
}

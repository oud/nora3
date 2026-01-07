package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Dossier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DossierDTO implements Serializable {

    private Long id;

    private String numDossierNor;

    private String numDossierGaia;

    private LocalTime receptionDateNor;

    private LocalTime validationDateNor;

    private String codeOrganisme;

    private String codeAgent;

    private String userCreation;

    private LocalTime creationDate;

    private String userMaj;

    private LocalTime majDate;

    private Integer numMaj;

    private DebiteurDTO debiteur;

    private CreancierDTO creancier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDossierNor() {
        return numDossierNor;
    }

    public void setNumDossierNor(String numDossierNor) {
        this.numDossierNor = numDossierNor;
    }

    public String getNumDossierGaia() {
        return numDossierGaia;
    }

    public void setNumDossierGaia(String numDossierGaia) {
        this.numDossierGaia = numDossierGaia;
    }

    public LocalTime getReceptionDateNor() {
        return receptionDateNor;
    }

    public void setReceptionDateNor(LocalTime receptionDateNor) {
        this.receptionDateNor = receptionDateNor;
    }

    public LocalTime getValidationDateNor() {
        return validationDateNor;
    }

    public void setValidationDateNor(LocalTime validationDateNor) {
        this.validationDateNor = validationDateNor;
    }

    public String getCodeOrganisme() {
        return codeOrganisme;
    }

    public void setCodeOrganisme(String codeOrganisme) {
        this.codeOrganisme = codeOrganisme;
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

    public DebiteurDTO getDebiteur() {
        return debiteur;
    }

    public void setDebiteur(DebiteurDTO debiteur) {
        this.debiteur = debiteur;
    }

    public CreancierDTO getCreancier() {
        return creancier;
    }

    public void setCreancier(CreancierDTO creancier) {
        this.creancier = creancier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DossierDTO)) {
            return false;
        }

        DossierDTO dossierDTO = (DossierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dossierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DossierDTO{" +
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
            ", debiteur=" + getDebiteur() +
            ", creancier=" + getCreancier() +
            "}";
    }
}

package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Demarche} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemarcheDTO implements Serializable {

    private Long id;

    private LocalDate demarcheDate;

    private String numDemarche;

    private String codeOrigine;

    private String codeStatut;

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

    public LocalDate getDemarcheDate() {
        return demarcheDate;
    }

    public void setDemarcheDate(LocalDate demarcheDate) {
        this.demarcheDate = demarcheDate;
    }

    public String getNumDemarche() {
        return numDemarche;
    }

    public void setNumDemarche(String numDemarche) {
        this.numDemarche = numDemarche;
    }

    public String getCodeOrigine() {
        return codeOrigine;
    }

    public void setCodeOrigine(String codeOrigine) {
        this.codeOrigine = codeOrigine;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
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
        if (!(o instanceof DemarcheDTO)) {
            return false;
        }

        DemarcheDTO demarcheDTO = (DemarcheDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demarcheDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemarcheDTO{" +
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
            ", dossier=" + getDossier() +
            "}";
    }
}

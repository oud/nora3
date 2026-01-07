package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Statut} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatutDTO implements Serializable {

    private Long id;

    private LocalTime statutDebutDate;

    private LocalTime statutFinDate;

    private String codeStatut;

    private String motifStatut;

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

    public LocalTime getStatutDebutDate() {
        return statutDebutDate;
    }

    public void setStatutDebutDate(LocalTime statutDebutDate) {
        this.statutDebutDate = statutDebutDate;
    }

    public LocalTime getStatutFinDate() {
        return statutFinDate;
    }

    public void setStatutFinDate(LocalTime statutFinDate) {
        this.statutFinDate = statutFinDate;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getMotifStatut() {
        return motifStatut;
    }

    public void setMotifStatut(String motifStatut) {
        this.motifStatut = motifStatut;
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
        if (!(o instanceof StatutDTO)) {
            return false;
        }

        StatutDTO statutDTO = (StatutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatutDTO{" +
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
            ", dossier=" + getDossier() +
            "}";
    }
}

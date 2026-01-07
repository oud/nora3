package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Enfant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnfantDTO implements Serializable {

    private Long id;

    private String nir;

    private Integer cleNir;

    private Integer numPersonneGaia;

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

    public String getNir() {
        return nir;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public Integer getCleNir() {
        return cleNir;
    }

    public void setCleNir(Integer cleNir) {
        this.cleNir = cleNir;
    }

    public Integer getNumPersonneGaia() {
        return numPersonneGaia;
    }

    public void setNumPersonneGaia(Integer numPersonneGaia) {
        this.numPersonneGaia = numPersonneGaia;
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
        if (!(o instanceof EnfantDTO)) {
            return false;
        }

        EnfantDTO enfantDTO = (EnfantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enfantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnfantDTO{" +
            "id=" + getId() +
            ", nir='" + getNir() + "'" +
            ", cleNir=" + getCleNir() +
            ", numPersonneGaia=" + getNumPersonneGaia() +
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

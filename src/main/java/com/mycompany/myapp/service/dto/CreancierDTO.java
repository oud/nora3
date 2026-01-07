package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Creancier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreancierDTO implements Serializable {

    private Long id;

    private String nir;

    private Integer cleNir;

    private Integer numAllocCristal;

    private Integer numPersonneCristal;

    private String codeOrganismeCristal;

    private LocalDate situationFamilialeDebutDate;

    private String codeSituationFamiliale;

    private String codeNationalite;

    private String codeAgent;

    private String userCreation;

    private LocalTime creationDate;

    private String userMaj;

    private LocalTime majDate;

    private Integer numMaj;

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

    public Integer getNumAllocCristal() {
        return numAllocCristal;
    }

    public void setNumAllocCristal(Integer numAllocCristal) {
        this.numAllocCristal = numAllocCristal;
    }

    public Integer getNumPersonneCristal() {
        return numPersonneCristal;
    }

    public void setNumPersonneCristal(Integer numPersonneCristal) {
        this.numPersonneCristal = numPersonneCristal;
    }

    public String getCodeOrganismeCristal() {
        return codeOrganismeCristal;
    }

    public void setCodeOrganismeCristal(String codeOrganismeCristal) {
        this.codeOrganismeCristal = codeOrganismeCristal;
    }

    public LocalDate getSituationFamilialeDebutDate() {
        return situationFamilialeDebutDate;
    }

    public void setSituationFamilialeDebutDate(LocalDate situationFamilialeDebutDate) {
        this.situationFamilialeDebutDate = situationFamilialeDebutDate;
    }

    public String getCodeSituationFamiliale() {
        return codeSituationFamiliale;
    }

    public void setCodeSituationFamiliale(String codeSituationFamiliale) {
        this.codeSituationFamiliale = codeSituationFamiliale;
    }

    public String getCodeNationalite() {
        return codeNationalite;
    }

    public void setCodeNationalite(String codeNationalite) {
        this.codeNationalite = codeNationalite;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreancierDTO)) {
            return false;
        }

        CreancierDTO creancierDTO = (CreancierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creancierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreancierDTO{" +
            "id=" + getId() +
            ", nir='" + getNir() + "'" +
            ", cleNir=" + getCleNir() +
            ", numAllocCristal=" + getNumAllocCristal() +
            ", numPersonneCristal=" + getNumPersonneCristal() +
            ", codeOrganismeCristal='" + getCodeOrganismeCristal() + "'" +
            ", situationFamilialeDebutDate='" + getSituationFamilialeDebutDate() + "'" +
            ", codeSituationFamiliale='" + getCodeSituationFamiliale() + "'" +
            ", codeNationalite='" + getCodeNationalite() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            "}";
    }
}

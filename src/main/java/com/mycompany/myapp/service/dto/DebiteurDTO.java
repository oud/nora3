package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Debiteur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DebiteurDTO implements Serializable {

    private Long id;

    private String nir;

    private Integer cleNir;

    private Integer numAllocCristal;

    private String codeOrganismeCristal;

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

    public String getCodeOrganismeCristal() {
        return codeOrganismeCristal;
    }

    public void setCodeOrganismeCristal(String codeOrganismeCristal) {
        this.codeOrganismeCristal = codeOrganismeCristal;
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
        if (!(o instanceof DebiteurDTO)) {
            return false;
        }

        DebiteurDTO debiteurDTO = (DebiteurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, debiteurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebiteurDTO{" +
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

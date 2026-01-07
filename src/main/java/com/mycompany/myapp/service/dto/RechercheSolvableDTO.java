package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RechercheSolvable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RechercheSolvableDTO implements Serializable {

    private Long id;

    private LocalTime rechercheSolvabiliteDebutDate;

    private String codeAgent;

    private String userCreation;

    private LocalTime creationDate;

    private String userMaj;

    private LocalTime majDate;

    private Integer numMaj;

    private DebiteurDTO debiteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getRechercheSolvabiliteDebutDate() {
        return rechercheSolvabiliteDebutDate;
    }

    public void setRechercheSolvabiliteDebutDate(LocalTime rechercheSolvabiliteDebutDate) {
        this.rechercheSolvabiliteDebutDate = rechercheSolvabiliteDebutDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RechercheSolvableDTO)) {
            return false;
        }

        RechercheSolvableDTO rechercheSolvableDTO = (RechercheSolvableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rechercheSolvableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RechercheSolvableDTO{" +
            "id=" + getId() +
            ", rechercheSolvabiliteDebutDate='" + getRechercheSolvabiliteDebutDate() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            ", debiteur=" + getDebiteur() +
            "}";
    }
}

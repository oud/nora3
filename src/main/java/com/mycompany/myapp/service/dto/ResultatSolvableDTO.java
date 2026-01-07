package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ResultatSolvable} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultatSolvableDTO implements Serializable {

    private Long id;

    private LocalDate moisSolvabiliteDate;

    private String codeEtatSolvabilite;

    private String codeAgent;

    private String userCreation;

    private LocalTime creationDate;

    private String userMaj;

    private LocalTime majDate;

    private Integer numMaj;

    private RechercheSolvableDTO rechercheSolvable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMoisSolvabiliteDate() {
        return moisSolvabiliteDate;
    }

    public void setMoisSolvabiliteDate(LocalDate moisSolvabiliteDate) {
        this.moisSolvabiliteDate = moisSolvabiliteDate;
    }

    public String getCodeEtatSolvabilite() {
        return codeEtatSolvabilite;
    }

    public void setCodeEtatSolvabilite(String codeEtatSolvabilite) {
        this.codeEtatSolvabilite = codeEtatSolvabilite;
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

    public RechercheSolvableDTO getRechercheSolvable() {
        return rechercheSolvable;
    }

    public void setRechercheSolvable(RechercheSolvableDTO rechercheSolvable) {
        this.rechercheSolvable = rechercheSolvable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultatSolvableDTO)) {
            return false;
        }

        ResultatSolvableDTO resultatSolvableDTO = (ResultatSolvableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resultatSolvableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatSolvableDTO{" +
            "id=" + getId() +
            ", moisSolvabiliteDate='" + getMoisSolvabiliteDate() + "'" +
            ", codeEtatSolvabilite='" + getCodeEtatSolvabilite() + "'" +
            ", codeAgent='" + getCodeAgent() + "'" +
            ", userCreation='" + getUserCreation() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", userMaj='" + getUserMaj() + "'" +
            ", majDate='" + getMajDate() + "'" +
            ", numMaj=" + getNumMaj() +
            ", rechercheSolvable=" + getRechercheSolvable() +
            "}";
    }
}

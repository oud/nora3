package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Situation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SituationDTO implements Serializable {

    private Long id;

    private LocalDate situationProDebutDate;

    private LocalDate situationProfinDate;

    private String codeSituation;

    private String commentaire;

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

    public LocalDate getSituationProDebutDate() {
        return situationProDebutDate;
    }

    public void setSituationProDebutDate(LocalDate situationProDebutDate) {
        this.situationProDebutDate = situationProDebutDate;
    }

    public LocalDate getSituationProfinDate() {
        return situationProfinDate;
    }

    public void setSituationProfinDate(LocalDate situationProfinDate) {
        this.situationProfinDate = situationProfinDate;
    }

    public String getCodeSituation() {
        return codeSituation;
    }

    public void setCodeSituation(String codeSituation) {
        this.codeSituation = codeSituation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        if (!(o instanceof SituationDTO)) {
            return false;
        }

        SituationDTO situationDTO = (SituationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, situationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SituationDTO{" +
            "id=" + getId() +
            ", situationProDebutDate='" + getSituationProDebutDate() + "'" +
            ", situationProfinDate='" + getSituationProfinDate() + "'" +
            ", codeSituation='" + getCodeSituation() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
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

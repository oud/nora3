package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Situation.
 */
@Entity
@Table(name = "situation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Situation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "situation_pro_debut_date")
    private LocalDate situationProDebutDate;

    @Column(name = "situation_profin_date")
    private LocalDate situationProfinDate;

    @Column(name = "code_situation")
    private String codeSituation;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "code_agent")
    private String codeAgent;

    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "creation_date")
    private LocalTime creationDate;

    @Column(name = "user_maj")
    private String userMaj;

    @Column(name = "maj_date")
    private LocalTime majDate;

    @Column(name = "num_maj")
    private Integer numMaj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "situations", "recherches", "dossier" }, allowSetters = true)
    private Debiteur debiteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Situation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSituationProDebutDate() {
        return this.situationProDebutDate;
    }

    public Situation situationProDebutDate(LocalDate situationProDebutDate) {
        this.setSituationProDebutDate(situationProDebutDate);
        return this;
    }

    public void setSituationProDebutDate(LocalDate situationProDebutDate) {
        this.situationProDebutDate = situationProDebutDate;
    }

    public LocalDate getSituationProfinDate() {
        return this.situationProfinDate;
    }

    public Situation situationProfinDate(LocalDate situationProfinDate) {
        this.setSituationProfinDate(situationProfinDate);
        return this;
    }

    public void setSituationProfinDate(LocalDate situationProfinDate) {
        this.situationProfinDate = situationProfinDate;
    }

    public String getCodeSituation() {
        return this.codeSituation;
    }

    public Situation codeSituation(String codeSituation) {
        this.setCodeSituation(codeSituation);
        return this;
    }

    public void setCodeSituation(String codeSituation) {
        this.codeSituation = codeSituation;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Situation commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Situation codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Situation userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Situation creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Situation userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Situation majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Situation numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Debiteur getDebiteur() {
        return this.debiteur;
    }

    public void setDebiteur(Debiteur debiteur) {
        this.debiteur = debiteur;
    }

    public Situation debiteur(Debiteur debiteur) {
        this.setDebiteur(debiteur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Situation)) {
            return false;
        }
        return getId() != null && getId().equals(((Situation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Situation{" +
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
            "}";
    }
}

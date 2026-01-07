package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enfant.
 */
@Entity
@Table(name = "enfant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enfant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nir")
    private String nir;

    @Column(name = "cle_nir")
    private Integer cleNir;

    @Column(name = "num_personne_gaia")
    private Integer numPersonneGaia;

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
    @JsonIgnoreProperties(value = { "debiteur", "creancier", "statuts", "enfants", "demarches", "defaillances" }, allowSetters = true)
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enfant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNir() {
        return this.nir;
    }

    public Enfant nir(String nir) {
        this.setNir(nir);
        return this;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public Integer getCleNir() {
        return this.cleNir;
    }

    public Enfant cleNir(Integer cleNir) {
        this.setCleNir(cleNir);
        return this;
    }

    public void setCleNir(Integer cleNir) {
        this.cleNir = cleNir;
    }

    public Integer getNumPersonneGaia() {
        return this.numPersonneGaia;
    }

    public Enfant numPersonneGaia(Integer numPersonneGaia) {
        this.setNumPersonneGaia(numPersonneGaia);
        return this;
    }

    public void setNumPersonneGaia(Integer numPersonneGaia) {
        this.numPersonneGaia = numPersonneGaia;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Enfant codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Enfant userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Enfant creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Enfant userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Enfant majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Enfant numMaj(Integer numMaj) {
        this.setNumMaj(numMaj);
        return this;
    }

    public void setNumMaj(Integer numMaj) {
        this.numMaj = numMaj;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Enfant dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enfant)) {
            return false;
        }
        return getId() != null && getId().equals(((Enfant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enfant{" +
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
            "}";
    }
}

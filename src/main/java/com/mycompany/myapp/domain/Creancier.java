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
 * A Creancier.
 */
@Entity
@Table(name = "creancier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Creancier implements Serializable {

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

    @Column(name = "num_alloc_cristal")
    private Integer numAllocCristal;

    @Column(name = "num_personne_cristal")
    private Integer numPersonneCristal;

    @Column(name = "code_organisme_cristal")
    private String codeOrganismeCristal;

    @Column(name = "situation_familiale_debut_date")
    private LocalDate situationFamilialeDebutDate;

    @Column(name = "code_situation_familiale")
    private String codeSituationFamiliale;

    @Column(name = "code_nationalite")
    private String codeNationalite;

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

    @JsonIgnoreProperties(value = { "debiteur", "creancier", "statuts", "enfants", "demarches", "defaillances" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "creancier")
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Creancier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNir() {
        return this.nir;
    }

    public Creancier nir(String nir) {
        this.setNir(nir);
        return this;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public Integer getCleNir() {
        return this.cleNir;
    }

    public Creancier cleNir(Integer cleNir) {
        this.setCleNir(cleNir);
        return this;
    }

    public void setCleNir(Integer cleNir) {
        this.cleNir = cleNir;
    }

    public Integer getNumAllocCristal() {
        return this.numAllocCristal;
    }

    public Creancier numAllocCristal(Integer numAllocCristal) {
        this.setNumAllocCristal(numAllocCristal);
        return this;
    }

    public void setNumAllocCristal(Integer numAllocCristal) {
        this.numAllocCristal = numAllocCristal;
    }

    public Integer getNumPersonneCristal() {
        return this.numPersonneCristal;
    }

    public Creancier numPersonneCristal(Integer numPersonneCristal) {
        this.setNumPersonneCristal(numPersonneCristal);
        return this;
    }

    public void setNumPersonneCristal(Integer numPersonneCristal) {
        this.numPersonneCristal = numPersonneCristal;
    }

    public String getCodeOrganismeCristal() {
        return this.codeOrganismeCristal;
    }

    public Creancier codeOrganismeCristal(String codeOrganismeCristal) {
        this.setCodeOrganismeCristal(codeOrganismeCristal);
        return this;
    }

    public void setCodeOrganismeCristal(String codeOrganismeCristal) {
        this.codeOrganismeCristal = codeOrganismeCristal;
    }

    public LocalDate getSituationFamilialeDebutDate() {
        return this.situationFamilialeDebutDate;
    }

    public Creancier situationFamilialeDebutDate(LocalDate situationFamilialeDebutDate) {
        this.setSituationFamilialeDebutDate(situationFamilialeDebutDate);
        return this;
    }

    public void setSituationFamilialeDebutDate(LocalDate situationFamilialeDebutDate) {
        this.situationFamilialeDebutDate = situationFamilialeDebutDate;
    }

    public String getCodeSituationFamiliale() {
        return this.codeSituationFamiliale;
    }

    public Creancier codeSituationFamiliale(String codeSituationFamiliale) {
        this.setCodeSituationFamiliale(codeSituationFamiliale);
        return this;
    }

    public void setCodeSituationFamiliale(String codeSituationFamiliale) {
        this.codeSituationFamiliale = codeSituationFamiliale;
    }

    public String getCodeNationalite() {
        return this.codeNationalite;
    }

    public Creancier codeNationalite(String codeNationalite) {
        this.setCodeNationalite(codeNationalite);
        return this;
    }

    public void setCodeNationalite(String codeNationalite) {
        this.codeNationalite = codeNationalite;
    }

    public String getCodeAgent() {
        return this.codeAgent;
    }

    public Creancier codeAgent(String codeAgent) {
        this.setCodeAgent(codeAgent);
        return this;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getUserCreation() {
        return this.userCreation;
    }

    public Creancier userCreation(String userCreation) {
        this.setUserCreation(userCreation);
        return this;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public LocalTime getCreationDate() {
        return this.creationDate;
    }

    public Creancier creationDate(LocalTime creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserMaj() {
        return this.userMaj;
    }

    public Creancier userMaj(String userMaj) {
        this.setUserMaj(userMaj);
        return this;
    }

    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }

    public LocalTime getMajDate() {
        return this.majDate;
    }

    public Creancier majDate(LocalTime majDate) {
        this.setMajDate(majDate);
        return this;
    }

    public void setMajDate(LocalTime majDate) {
        this.majDate = majDate;
    }

    public Integer getNumMaj() {
        return this.numMaj;
    }

    public Creancier numMaj(Integer numMaj) {
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
        if (this.dossier != null) {
            this.dossier.setCreancier(null);
        }
        if (dossier != null) {
            dossier.setCreancier(this);
        }
        this.dossier = dossier;
    }

    public Creancier dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Creancier)) {
            return false;
        }
        return getId() != null && getId().equals(((Creancier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Creancier{" +
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

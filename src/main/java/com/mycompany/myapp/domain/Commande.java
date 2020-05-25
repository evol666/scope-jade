package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "dtexecution")
    private LocalDate dtexecution;

    @Column(name = "statut")
    private String statut;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "volumeveh")
    private Integer volumeveh;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Commande libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDtexecution() {
        return dtexecution;
    }

    public Commande dtexecution(LocalDate dtexecution) {
        this.dtexecution = dtexecution;
        return this;
    }

    public void setDtexecution(LocalDate dtexecution) {
        this.dtexecution = dtexecution;
    }

    public String getStatut() {
        return statut;
    }

    public Commande statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getVolume() {
        return volume;
    }

    public Commande volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getVolumeveh() {
        return volumeveh;
    }

    public Commande volumeveh(Integer volumeveh) {
        this.volumeveh = volumeveh;
        return this;
    }

    public void setVolumeveh(Integer volumeveh) {
        this.volumeveh = volumeveh;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dtexecution='" + getDtexecution() + "'" +
            ", statut='" + getStatut() + "'" +
            ", volume=" + getVolume() +
            ", volumeveh=" + getVolumeveh() +
            "}";
    }
}

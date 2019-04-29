package dev.eazylife.s_tracking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Voitures.
 */
@Entity
@Table(name = "voitures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voitures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "plaque", nullable = false)
    private String plaque;

    @Column(name = "marque")
    private String marque;

    @Column(name = "couleur")
    private String couleur;

    @OneToOne
    @JoinColumn(unique = true)
    private Devices devices;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaque() {
        return plaque;
    }

    public Voitures plaque(String plaque) {
        this.plaque = plaque;
        return this;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getMarque() {
        return marque;
    }

    public Voitures marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public Voitures couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Devices getDevices() {
        return devices;
    }

    public Voitures devices(Devices devices) {
        this.devices = devices;
        return this;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Voitures voitures = (Voitures) o;
        if (voitures.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voitures.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Voitures{" +
            "id=" + getId() +
            ", plaque='" + getPlaque() + "'" +
            ", marque='" + getMarque() + "'" +
            ", couleur='" + getCouleur() + "'" +
            "}";
    }
}

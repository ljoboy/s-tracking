package dev.eazylife.s_tracking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import dev.eazylife.s_tracking.domain.enumeration.Types;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_abonnement")
    private Instant dateAbonnement;

    @Size(min = 3)
    @Column(name = "province")
    private String province;

    @Size(min = 3)
    @Column(name = "ville")
    private String ville;

    @Size(min = 3)
    @Column(name = "adresse")
    private String adresse;

    @NotNull
    @Size(min = 6)
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "types")
    private Types types;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @NotNull
    @Pattern(regexp = "^\\S+@\\S+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rccm")
    private String rccm;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Clients nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDateAbonnement() {
        return dateAbonnement;
    }

    public Clients dateAbonnement(Instant dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
        return this;
    }

    public void setDateAbonnement(Instant dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }

    public String getProvince() {
        return province;
    }

    public Clients province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getVille() {
        return ville;
    }

    public Clients ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public Clients adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public Clients password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Types getTypes() {
        return types;
    }

    public Clients types(Types types) {
        this.types = types;
        return this;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Clients telephone(Long telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Clients email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRccm() {
        return rccm;
    }

    public Clients rccm(String rccm) {
        this.rccm = rccm;
        return this;
    }

    public void setRccm(String rccm) {
        this.rccm = rccm;
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
        Clients clients = (Clients) o;
        if (clients.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clients.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", dateAbonnement='" + getDateAbonnement() + "'" +
            ", province='" + getProvince() + "'" +
            ", ville='" + getVille() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", password='" + getPassword() + "'" +
            ", types='" + getTypes() + "'" +
            ", telephone=" + getTelephone() +
            ", email='" + getEmail() + "'" +
            ", rccm='" + getRccm() + "'" +
            "}";
    }
}

package dev.eazylife.s_tracking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Devices.
 */
@Entity
@Table(name = "devices")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Devices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "imei", nullable = false)
    private Long imei;

    @NotNull
    @Column(name = "numero_sim", nullable = false)
    private Long numeroSim;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Clients clients;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImei() {
        return imei;
    }

    public Devices imei(Long imei) {
        this.imei = imei;
        return this;
    }

    public void setImei(Long imei) {
        this.imei = imei;
    }

    public Long getNumeroSim() {
        return numeroSim;
    }

    public Devices numeroSim(Long numeroSim) {
        this.numeroSim = numeroSim;
        return this;
    }

    public void setNumeroSim(Long numeroSim) {
        this.numeroSim = numeroSim;
    }

    public Clients getClients() {
        return clients;
    }

    public Devices clients(Clients clients) {
        this.clients = clients;
        return this;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
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
        Devices devices = (Devices) o;
        if (devices.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devices.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Devices{" +
            "id=" + getId() +
            ", imei=" + getImei() +
            ", numeroSim=" + getNumeroSim() +
            "}";
    }
}

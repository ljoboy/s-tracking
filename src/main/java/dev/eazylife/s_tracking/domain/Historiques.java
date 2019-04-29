package dev.eazylife.s_tracking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Historiques.
 */
@Entity
@Table(name = "historiques")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Historiques implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_historique")
    private Instant dateHistorique;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Devices devices;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateHistorique() {
        return dateHistorique;
    }

    public Historiques dateHistorique(Instant dateHistorique) {
        this.dateHistorique = dateHistorique;
        return this;
    }

    public void setDateHistorique(Instant dateHistorique) {
        this.dateHistorique = dateHistorique;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Historiques longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Historiques latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Devices getDevices() {
        return devices;
    }

    public Historiques devices(Devices devices) {
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
        Historiques historiques = (Historiques) o;
        if (historiques.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiques.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Historiques{" +
            "id=" + getId() +
            ", dateHistorique='" + getDateHistorique() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}

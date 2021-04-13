package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Maestro.
 */
@Entity
@Table(name = "maestro")
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_maestro", nullable = false, unique = true)
    private String idMaestro;

    @NotNull
    @Column(name = "maestro", nullable = false)
    private String maestro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Maestro id(Long id) {
        this.id = id;
        return this;
    }

    public String getIdMaestro() {
        return this.idMaestro;
    }

    public Maestro idMaestro(String idMaestro) {
        this.idMaestro = idMaestro;
        return this;
    }

    public void setIdMaestro(String idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getMaestro() {
        return this.maestro;
    }

    public Maestro maestro(String maestro) {
        this.maestro = maestro;
        return this;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public EstatusRegistro getEstatus() {
        return this.estatus;
    }

    public Maestro estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Maestro)) {
            return false;
        }
        return id != null && id.equals(((Maestro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Maestro{" +
            "id=" + getId() +
            ", idMaestro='" + getIdMaestro() + "'" +
            ", maestro='" + getMaestro() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}

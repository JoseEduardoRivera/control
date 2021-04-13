package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Maestro} entity.
 */
public class MaestroDTO implements Serializable {

    private Long id;

    @NotNull
    private String idMaestro;

    @NotNull
    private String maestro;

    @NotNull
    private EstatusRegistro estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(String idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaestroDTO)) {
            return false;
        }

        MaestroDTO maestroDTO = (MaestroDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, maestroDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaestroDTO{" +
            "id=" + getId() +
            ", idMaestro='" + getIdMaestro() + "'" +
            ", maestro='" + getMaestro() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}

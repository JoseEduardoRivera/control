package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Materia} entity.
 */
public class MateriaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idMateria;

    @NotNull
    private String materia;

    @NotNull
    private String abreviatura;

    @NotNull
    private EstatusRegistro estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
        if (!(o instanceof MateriaDTO)) {
            return false;
        }

        MateriaDTO materiaDTO = (MateriaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, materiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaDTO{" +
            "id=" + getId() +
            ", idMateria='" + getIdMateria() + "'" +
            ", materia='" + getMateria() + "'" +
            ", abreviatura='" + getAbreviatura() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}

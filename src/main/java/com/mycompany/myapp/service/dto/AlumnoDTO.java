package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Alumno} entity.
 */
public class AlumnoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idAlumno;

    @NotNull
    private String alumno;

    @NotNull
    private EstatusRegistro estatus;

    private MateriaDTO materia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public EstatusRegistro getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public MateriaDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriaDTO materia) {
        this.materia = materia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlumnoDTO)) {
            return false;
        }

        AlumnoDTO alumnoDTO = (AlumnoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alumnoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlumnoDTO{" +
            "id=" + getId() +
            ", idAlumno='" + getIdAlumno() + "'" +
            ", alumno='" + getAlumno() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", materia=" + getMateria() +
            "}";
    }
}

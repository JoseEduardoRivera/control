package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Alumno.
 */
@Entity
@Table(name = "alumno")
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_alumno", nullable = false, unique = true)
    private String idAlumno;

    @NotNull
    @Column(name = "alumno", nullable = false)
    private String alumno;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alumnos" }, allowSetters = true)
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno id(Long id) {
        this.id = id;
        return this;
    }

    public String getIdAlumno() {
        return this.idAlumno;
    }

    public Alumno idAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
        return this;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getAlumno() {
        return this.alumno;
    }

    public Alumno alumno(String alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public EstatusRegistro getEstatus() {
        return this.estatus;
    }

    public Alumno estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public Alumno materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alumno)) {
            return false;
        }
        return id != null && id.equals(((Alumno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alumno{" +
            "id=" + getId() +
            ", idAlumno='" + getIdAlumno() + "'" +
            ", alumno='" + getAlumno() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}

package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Materia.
 */
@Entity
@Table(name = "materia")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_materia", nullable = false, unique = true)
    private String idMateria;

    @NotNull
    @Column(name = "materia", nullable = false)
    private String materia;

    @NotNull
    @Column(name = "abreviatura", nullable = false)
    private String abreviatura;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusRegistro estatus;

    @OneToMany(mappedBy = "materia")
    @JsonIgnoreProperties(value = { "materia" }, allowSetters = true)
    private Set<Alumno> alumnos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia id(Long id) {
        this.id = id;
        return this;
    }

    public String getIdMateria() {
        return this.idMateria;
    }

    public Materia idMateria(String idMateria) {
        this.idMateria = idMateria;
        return this;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getMateria() {
        return this.materia;
    }

    public Materia materia(String materia) {
        this.materia = materia;
        return this;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAbreviatura() {
        return this.abreviatura;
    }

    public Materia abreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
        return this;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public EstatusRegistro getEstatus() {
        return this.estatus;
    }

    public Materia estatus(EstatusRegistro estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(EstatusRegistro estatus) {
        this.estatus = estatus;
    }

    public Set<Alumno> getAlumnos() {
        return this.alumnos;
    }

    public Materia alumnos(Set<Alumno> alumnos) {
        this.setAlumnos(alumnos);
        return this;
    }

    public Materia addAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
        alumno.setMateria(this);
        return this;
    }

    public Materia removeAlumno(Alumno alumno) {
        this.alumnos.remove(alumno);
        alumno.setMateria(null);
        return this;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        if (this.alumnos != null) {
            this.alumnos.forEach(i -> i.setMateria(null));
        }
        if (alumnos != null) {
            alumnos.forEach(i -> i.setMateria(this));
        }
        this.alumnos = alumnos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materia)) {
            return false;
        }
        return id != null && id.equals(((Materia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materia{" +
            "id=" + getId() +
            ", idMateria='" + getIdMateria() + "'" +
            ", materia='" + getMateria() + "'" +
            ", abreviatura='" + getAbreviatura() + "'" +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}

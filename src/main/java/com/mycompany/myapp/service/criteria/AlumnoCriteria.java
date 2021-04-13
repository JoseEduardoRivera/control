package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Alumno} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AlumnoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alumnos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlumnoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstatusRegistro
     */
    public static class EstatusRegistroFilter extends Filter<EstatusRegistro> {

        public EstatusRegistroFilter() {}

        public EstatusRegistroFilter(EstatusRegistroFilter filter) {
            super(filter);
        }

        @Override
        public EstatusRegistroFilter copy() {
            return new EstatusRegistroFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idAlumno;

    private StringFilter alumno;

    private EstatusRegistroFilter estatus;

    private LongFilter materiaId;

    public AlumnoCriteria() {}

    public AlumnoCriteria(AlumnoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idAlumno = other.idAlumno == null ? null : other.idAlumno.copy();
        this.alumno = other.alumno == null ? null : other.alumno.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.materiaId = other.materiaId == null ? null : other.materiaId.copy();
    }

    @Override
    public AlumnoCriteria copy() {
        return new AlumnoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdAlumno() {
        return idAlumno;
    }

    public StringFilter idAlumno() {
        if (idAlumno == null) {
            idAlumno = new StringFilter();
        }
        return idAlumno;
    }

    public void setIdAlumno(StringFilter idAlumno) {
        this.idAlumno = idAlumno;
    }

    public StringFilter getAlumno() {
        return alumno;
    }

    public StringFilter alumno() {
        if (alumno == null) {
            alumno = new StringFilter();
        }
        return alumno;
    }

    public void setAlumno(StringFilter alumno) {
        this.alumno = alumno;
    }

    public EstatusRegistroFilter getEstatus() {
        return estatus;
    }

    public EstatusRegistroFilter estatus() {
        if (estatus == null) {
            estatus = new EstatusRegistroFilter();
        }
        return estatus;
    }

    public void setEstatus(EstatusRegistroFilter estatus) {
        this.estatus = estatus;
    }

    public LongFilter getMateriaId() {
        return materiaId;
    }

    public LongFilter materiaId() {
        if (materiaId == null) {
            materiaId = new LongFilter();
        }
        return materiaId;
    }

    public void setMateriaId(LongFilter materiaId) {
        this.materiaId = materiaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlumnoCriteria that = (AlumnoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idAlumno, that.idAlumno) &&
            Objects.equals(alumno, that.alumno) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(materiaId, that.materiaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idAlumno, alumno, estatus, materiaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlumnoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idAlumno != null ? "idAlumno=" + idAlumno + ", " : "") +
            (alumno != null ? "alumno=" + alumno + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            (materiaId != null ? "materiaId=" + materiaId + ", " : "") +
            "}";
    }
}

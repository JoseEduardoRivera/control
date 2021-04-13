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
 * Criteria class for the {@link com.mycompany.myapp.domain.Materia} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MateriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /materias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MateriaCriteria implements Serializable, Criteria {

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

    private StringFilter idMateria;

    private StringFilter materia;

    private StringFilter abreviatura;

    private EstatusRegistroFilter estatus;

    private LongFilter alumnoId;

    public MateriaCriteria() {}

    public MateriaCriteria(MateriaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idMateria = other.idMateria == null ? null : other.idMateria.copy();
        this.materia = other.materia == null ? null : other.materia.copy();
        this.abreviatura = other.abreviatura == null ? null : other.abreviatura.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.alumnoId = other.alumnoId == null ? null : other.alumnoId.copy();
    }

    @Override
    public MateriaCriteria copy() {
        return new MateriaCriteria(this);
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

    public StringFilter getIdMateria() {
        return idMateria;
    }

    public StringFilter idMateria() {
        if (idMateria == null) {
            idMateria = new StringFilter();
        }
        return idMateria;
    }

    public void setIdMateria(StringFilter idMateria) {
        this.idMateria = idMateria;
    }

    public StringFilter getMateria() {
        return materia;
    }

    public StringFilter materia() {
        if (materia == null) {
            materia = new StringFilter();
        }
        return materia;
    }

    public void setMateria(StringFilter materia) {
        this.materia = materia;
    }

    public StringFilter getAbreviatura() {
        return abreviatura;
    }

    public StringFilter abreviatura() {
        if (abreviatura == null) {
            abreviatura = new StringFilter();
        }
        return abreviatura;
    }

    public void setAbreviatura(StringFilter abreviatura) {
        this.abreviatura = abreviatura;
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

    public LongFilter getAlumnoId() {
        return alumnoId;
    }

    public LongFilter alumnoId() {
        if (alumnoId == null) {
            alumnoId = new LongFilter();
        }
        return alumnoId;
    }

    public void setAlumnoId(LongFilter alumnoId) {
        this.alumnoId = alumnoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MateriaCriteria that = (MateriaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idMateria, that.idMateria) &&
            Objects.equals(materia, that.materia) &&
            Objects.equals(abreviatura, that.abreviatura) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(alumnoId, that.alumnoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMateria, materia, abreviatura, estatus, alumnoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idMateria != null ? "idMateria=" + idMateria + ", " : "") +
            (materia != null ? "materia=" + materia + ", " : "") +
            (abreviatura != null ? "abreviatura=" + abreviatura + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            (alumnoId != null ? "alumnoId=" + alumnoId + ", " : "") +
            "}";
    }
}

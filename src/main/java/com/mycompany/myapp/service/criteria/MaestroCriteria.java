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
 * Criteria class for the {@link com.mycompany.myapp.domain.Maestro} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MaestroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /maestros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MaestroCriteria implements Serializable, Criteria {

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

    private StringFilter idMaestro;

    private StringFilter maestro;

    private EstatusRegistroFilter estatus;

    public MaestroCriteria() {}

    public MaestroCriteria(MaestroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idMaestro = other.idMaestro == null ? null : other.idMaestro.copy();
        this.maestro = other.maestro == null ? null : other.maestro.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
    }

    @Override
    public MaestroCriteria copy() {
        return new MaestroCriteria(this);
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

    public StringFilter getIdMaestro() {
        return idMaestro;
    }

    public StringFilter idMaestro() {
        if (idMaestro == null) {
            idMaestro = new StringFilter();
        }
        return idMaestro;
    }

    public void setIdMaestro(StringFilter idMaestro) {
        this.idMaestro = idMaestro;
    }

    public StringFilter getMaestro() {
        return maestro;
    }

    public StringFilter maestro() {
        if (maestro == null) {
            maestro = new StringFilter();
        }
        return maestro;
    }

    public void setMaestro(StringFilter maestro) {
        this.maestro = maestro;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MaestroCriteria that = (MaestroCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idMaestro, that.idMaestro) &&
            Objects.equals(maestro, that.maestro) &&
            Objects.equals(estatus, that.estatus)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMaestro, maestro, estatus);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaestroCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idMaestro != null ? "idMaestro=" + idMaestro + ", " : "") +
            (maestro != null ? "maestro=" + maestro + ", " : "") +
            (estatus != null ? "estatus=" + estatus + ", " : "") +
            "}";
    }
}

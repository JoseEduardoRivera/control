package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Alumno;
import com.mycompany.myapp.repository.AlumnoRepository;
import com.mycompany.myapp.service.criteria.AlumnoCriteria;
import com.mycompany.myapp.service.dto.AlumnoDTO;
import com.mycompany.myapp.service.mapper.AlumnoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Alumno} entities in the database.
 * The main input is a {@link AlumnoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlumnoDTO} or a {@link Page} of {@link AlumnoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlumnoQueryService extends QueryService<Alumno> {

    private final Logger log = LoggerFactory.getLogger(AlumnoQueryService.class);

    private final AlumnoRepository alumnoRepository;

    private final AlumnoMapper alumnoMapper;

    public AlumnoQueryService(AlumnoRepository alumnoRepository, AlumnoMapper alumnoMapper) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoMapper = alumnoMapper;
    }

    /**
     * Return a {@link List} of {@link AlumnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlumnoDTO> findByCriteria(AlumnoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Alumno> specification = createSpecification(criteria);
        return alumnoMapper.toDto(alumnoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlumnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlumnoDTO> findByCriteria(AlumnoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Alumno> specification = createSpecification(criteria);
        return alumnoRepository.findAll(specification, page).map(alumnoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlumnoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Alumno> specification = createSpecification(criteria);
        return alumnoRepository.count(specification);
    }

    /**
     * Function to convert {@link AlumnoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Alumno> createSpecification(AlumnoCriteria criteria) {
        Specification<Alumno> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Alumno_.id));
            }
            if (criteria.getIdAlumno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdAlumno(), Alumno_.idAlumno));
            }
            if (criteria.getAlumno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlumno(), Alumno_.alumno));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatus(), Alumno_.estatus));
            }
            if (criteria.getMateriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMateriaId(), root -> root.join(Alumno_.materia, JoinType.LEFT).get(Materia_.id))
                    );
            }
        }
        return specification;
    }
}

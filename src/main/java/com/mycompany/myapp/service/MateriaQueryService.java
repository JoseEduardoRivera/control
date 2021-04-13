package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Materia;
import com.mycompany.myapp.repository.MateriaRepository;
import com.mycompany.myapp.service.criteria.MateriaCriteria;
import com.mycompany.myapp.service.dto.MateriaDTO;
import com.mycompany.myapp.service.mapper.MateriaMapper;
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
 * Service for executing complex queries for {@link Materia} entities in the database.
 * The main input is a {@link MateriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MateriaDTO} or a {@link Page} of {@link MateriaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MateriaQueryService extends QueryService<Materia> {

    private final Logger log = LoggerFactory.getLogger(MateriaQueryService.class);

    private final MateriaRepository materiaRepository;

    private final MateriaMapper materiaMapper;

    public MateriaQueryService(MateriaRepository materiaRepository, MateriaMapper materiaMapper) {
        this.materiaRepository = materiaRepository;
        this.materiaMapper = materiaMapper;
    }

    /**
     * Return a {@link List} of {@link MateriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MateriaDTO> findByCriteria(MateriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Materia> specification = createSpecification(criteria);
        return materiaMapper.toDto(materiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MateriaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MateriaDTO> findByCriteria(MateriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Materia> specification = createSpecification(criteria);
        return materiaRepository.findAll(specification, page).map(materiaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MateriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Materia> specification = createSpecification(criteria);
        return materiaRepository.count(specification);
    }

    /**
     * Function to convert {@link MateriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Materia> createSpecification(MateriaCriteria criteria) {
        Specification<Materia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Materia_.id));
            }
            if (criteria.getIdMateria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMateria(), Materia_.idMateria));
            }
            if (criteria.getMateria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMateria(), Materia_.materia));
            }
            if (criteria.getAbreviatura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbreviatura(), Materia_.abreviatura));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatus(), Materia_.estatus));
            }
            if (criteria.getAlumnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlumnoId(), root -> root.join(Materia_.alumnos, JoinType.LEFT).get(Alumno_.id))
                    );
            }
        }
        return specification;
    }
}

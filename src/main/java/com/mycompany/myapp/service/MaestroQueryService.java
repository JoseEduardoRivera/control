package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Maestro;
import com.mycompany.myapp.repository.MaestroRepository;
import com.mycompany.myapp.service.criteria.MaestroCriteria;
import com.mycompany.myapp.service.dto.MaestroDTO;
import com.mycompany.myapp.service.mapper.MaestroMapper;
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
 * Service for executing complex queries for {@link Maestro} entities in the database.
 * The main input is a {@link MaestroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaestroDTO} or a {@link Page} of {@link MaestroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaestroQueryService extends QueryService<Maestro> {

    private final Logger log = LoggerFactory.getLogger(MaestroQueryService.class);

    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    public MaestroQueryService(MaestroRepository maestroRepository, MaestroMapper maestroMapper) {
        this.maestroRepository = maestroRepository;
        this.maestroMapper = maestroMapper;
    }

    /**
     * Return a {@link List} of {@link MaestroDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaestroDTO> findByCriteria(MaestroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Maestro> specification = createSpecification(criteria);
        return maestroMapper.toDto(maestroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MaestroDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaestroDTO> findByCriteria(MaestroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Maestro> specification = createSpecification(criteria);
        return maestroRepository.findAll(specification, page).map(maestroMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaestroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Maestro> specification = createSpecification(criteria);
        return maestroRepository.count(specification);
    }

    /**
     * Function to convert {@link MaestroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Maestro> createSpecification(MaestroCriteria criteria) {
        Specification<Maestro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Maestro_.id));
            }
            if (criteria.getIdMaestro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMaestro(), Maestro_.idMaestro));
            }
            if (criteria.getMaestro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaestro(), Maestro_.maestro));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatus(), Maestro_.estatus));
            }
        }
        return specification;
    }
}

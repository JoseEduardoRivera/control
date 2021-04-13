package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MateriaRepository;
import com.mycompany.myapp.service.MateriaQueryService;
import com.mycompany.myapp.service.MateriaService;
import com.mycompany.myapp.service.criteria.MateriaCriteria;
import com.mycompany.myapp.service.dto.MateriaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Materia}.
 */
@RestController
@RequestMapping("/api")
public class MateriaResource {

    private final Logger log = LoggerFactory.getLogger(MateriaResource.class);

    private static final String ENTITY_NAME = "materia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriaService materiaService;

    private final MateriaRepository materiaRepository;

    private final MateriaQueryService materiaQueryService;

    public MateriaResource(MateriaService materiaService, MateriaRepository materiaRepository, MateriaQueryService materiaQueryService) {
        this.materiaService = materiaService;
        this.materiaRepository = materiaRepository;
        this.materiaQueryService = materiaQueryService;
    }

    /**
     * {@code POST  /materias} : Create a new materia.
     *
     * @param materiaDTO the materiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiaDTO, or with status {@code 400 (Bad Request)} if the materia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materias")
    public ResponseEntity<MateriaDTO> createMateria(@Valid @RequestBody MateriaDTO materiaDTO) throws URISyntaxException {
        log.debug("REST request to save Materia : {}", materiaDTO);
        if (materiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new materia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MateriaDTO result = materiaService.save(materiaDTO);
        return ResponseEntity
            .created(new URI("/api/materias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materias/:id} : Updates an existing materia.
     *
     * @param id the id of the materiaDTO to save.
     * @param materiaDTO the materiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaDTO,
     * or with status {@code 400 (Bad Request)} if the materiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materias/{id}")
    public ResponseEntity<MateriaDTO> updateMateria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MateriaDTO materiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Materia : {}, {}", id, materiaDTO);
        if (materiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MateriaDTO result = materiaService.save(materiaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /materias/:id} : Partial updates given fields of an existing materia, field will ignore if it is null
     *
     * @param id the id of the materiaDTO to save.
     * @param materiaDTO the materiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaDTO,
     * or with status {@code 400 (Bad Request)} if the materiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the materiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MateriaDTO> partialUpdateMateria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MateriaDTO materiaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Materia partially : {}, {}", id, materiaDTO);
        if (materiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MateriaDTO> result = materiaService.partialUpdate(materiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /materias} : get all the materias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materias in body.
     */
    @GetMapping("/materias")
    public ResponseEntity<List<MateriaDTO>> getAllMaterias(MateriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Materias by criteria: {}", criteria);
        Page<MateriaDTO> page = materiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materias/count} : count all the materias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/materias/count")
    public ResponseEntity<Long> countMaterias(MateriaCriteria criteria) {
        log.debug("REST request to count Materias by criteria: {}", criteria);
        return ResponseEntity.ok().body(materiaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /materias/:id} : get the "id" materia.
     *
     * @param id the id of the materiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materias/{id}")
    public ResponseEntity<MateriaDTO> getMateria(@PathVariable Long id) {
        log.debug("REST request to get Materia : {}", id);
        Optional<MateriaDTO> materiaDTO = materiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materiaDTO);
    }

    /**
     * {@code DELETE  /materias/:id} : delete the "id" materia.
     *
     * @param id the id of the materiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materias/{id}")
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        log.debug("REST request to delete Materia : {}", id);
        materiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

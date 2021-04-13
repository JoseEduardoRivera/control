package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AlumnoRepository;
import com.mycompany.myapp.service.AlumnoQueryService;
import com.mycompany.myapp.service.AlumnoService;
import com.mycompany.myapp.service.criteria.AlumnoCriteria;
import com.mycompany.myapp.service.dto.AlumnoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Alumno}.
 */
@RestController
@RequestMapping("/api")
public class AlumnoResource {

    private final Logger log = LoggerFactory.getLogger(AlumnoResource.class);

    private static final String ENTITY_NAME = "alumno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlumnoService alumnoService;

    private final AlumnoRepository alumnoRepository;

    private final AlumnoQueryService alumnoQueryService;

    public AlumnoResource(AlumnoService alumnoService, AlumnoRepository alumnoRepository, AlumnoQueryService alumnoQueryService) {
        this.alumnoService = alumnoService;
        this.alumnoRepository = alumnoRepository;
        this.alumnoQueryService = alumnoQueryService;
    }

    /**
     * {@code POST  /alumnos} : Create a new alumno.
     *
     * @param alumnoDTO the alumnoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alumnoDTO, or with status {@code 400 (Bad Request)} if the alumno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alumnos")
    public ResponseEntity<AlumnoDTO> createAlumno(@Valid @RequestBody AlumnoDTO alumnoDTO) throws URISyntaxException {
        log.debug("REST request to save Alumno : {}", alumnoDTO);
        if (alumnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alumno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlumnoDTO result = alumnoService.save(alumnoDTO);
        return ResponseEntity
            .created(new URI("/api/alumnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alumnos/:id} : Updates an existing alumno.
     *
     * @param id the id of the alumnoDTO to save.
     * @param alumnoDTO the alumnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alumnoDTO,
     * or with status {@code 400 (Bad Request)} if the alumnoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alumnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoDTO> updateAlumno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlumnoDTO alumnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Alumno : {}, {}", id, alumnoDTO);
        if (alumnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alumnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alumnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlumnoDTO result = alumnoService.save(alumnoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alumnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alumnos/:id} : Partial updates given fields of an existing alumno, field will ignore if it is null
     *
     * @param id the id of the alumnoDTO to save.
     * @param alumnoDTO the alumnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alumnoDTO,
     * or with status {@code 400 (Bad Request)} if the alumnoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alumnoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alumnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alumnos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AlumnoDTO> partialUpdateAlumno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlumnoDTO alumnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Alumno partially : {}, {}", id, alumnoDTO);
        if (alumnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alumnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alumnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlumnoDTO> result = alumnoService.partialUpdate(alumnoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alumnoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alumnos} : get all the alumnos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alumnos in body.
     */
    @GetMapping("/alumnos")
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos(AlumnoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Alumnos by criteria: {}", criteria);
        Page<AlumnoDTO> page = alumnoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alumnos/count} : count all the alumnos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alumnos/count")
    public ResponseEntity<Long> countAlumnos(AlumnoCriteria criteria) {
        log.debug("REST request to count Alumnos by criteria: {}", criteria);
        return ResponseEntity.ok().body(alumnoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alumnos/:id} : get the "id" alumno.
     *
     * @param id the id of the alumnoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alumnoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable Long id) {
        log.debug("REST request to get Alumno : {}", id);
        Optional<AlumnoDTO> alumnoDTO = alumnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alumnoDTO);
    }

    /**
     * {@code DELETE  /alumnos/:id} : delete the "id" alumno.
     *
     * @param id the id of the alumnoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        log.debug("REST request to delete Alumno : {}", id);
        alumnoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

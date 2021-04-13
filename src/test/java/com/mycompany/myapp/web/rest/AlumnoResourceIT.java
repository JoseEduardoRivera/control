package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Alumno;
import com.mycompany.myapp.domain.Materia;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.AlumnoRepository;
import com.mycompany.myapp.service.criteria.AlumnoCriteria;
import com.mycompany.myapp.service.dto.AlumnoDTO;
import com.mycompany.myapp.service.mapper.AlumnoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AlumnoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlumnoResourceIT {

    private static final String DEFAULT_ID_ALUMNO = "AAAAAAAAAA";
    private static final String UPDATED_ID_ALUMNO = "BBBBBBBBBB";

    private static final String DEFAULT_ALUMNO = "AAAAAAAAAA";
    private static final String UPDATED_ALUMNO = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    private static final String ENTITY_API_URL = "/api/alumnos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AlumnoMapper alumnoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlumnoMockMvc;

    private Alumno alumno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alumno createEntity(EntityManager em) {
        Alumno alumno = new Alumno().idAlumno(DEFAULT_ID_ALUMNO).alumno(DEFAULT_ALUMNO).estatus(DEFAULT_ESTATUS);
        return alumno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alumno createUpdatedEntity(EntityManager em) {
        Alumno alumno = new Alumno().idAlumno(UPDATED_ID_ALUMNO).alumno(UPDATED_ALUMNO).estatus(UPDATED_ESTATUS);
        return alumno;
    }

    @BeforeEach
    public void initTest() {
        alumno = createEntity(em);
    }

    @Test
    @Transactional
    void createAlumno() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();
        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);
        restAlumnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isCreated());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate + 1);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getIdAlumno()).isEqualTo(DEFAULT_ID_ALUMNO);
        assertThat(testAlumno.getAlumno()).isEqualTo(DEFAULT_ALUMNO);
        assertThat(testAlumno.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    void createAlumnoWithExistingId() throws Exception {
        // Create the Alumno with an existing ID
        alumno.setId(1L);
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdAlumnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setIdAlumno(null);

        // Create the Alumno, which fails.
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        restAlumnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlumnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setAlumno(null);

        // Create the Alumno, which fails.
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        restAlumnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumnoRepository.findAll().size();
        // set the field null
        alumno.setEstatus(null);

        // Create the Alumno, which fails.
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        restAlumnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isBadRequest());

        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlumnos() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAlumno").value(hasItem(DEFAULT_ID_ALUMNO)))
            .andExpect(jsonPath("$.[*].alumno").value(hasItem(DEFAULT_ALUMNO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    void getAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get the alumno
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL_ID, alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alumno.getId().intValue()))
            .andExpect(jsonPath("$.idAlumno").value(DEFAULT_ID_ALUMNO))
            .andExpect(jsonPath("$.alumno").value(DEFAULT_ALUMNO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    void getAlumnosByIdFiltering() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        Long id = alumno.getId();

        defaultAlumnoShouldBeFound("id.equals=" + id);
        defaultAlumnoShouldNotBeFound("id.notEquals=" + id);

        defaultAlumnoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlumnoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlumnoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlumnoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoIsEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno equals to DEFAULT_ID_ALUMNO
        defaultAlumnoShouldBeFound("idAlumno.equals=" + DEFAULT_ID_ALUMNO);

        // Get all the alumnoList where idAlumno equals to UPDATED_ID_ALUMNO
        defaultAlumnoShouldNotBeFound("idAlumno.equals=" + UPDATED_ID_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno not equals to DEFAULT_ID_ALUMNO
        defaultAlumnoShouldNotBeFound("idAlumno.notEquals=" + DEFAULT_ID_ALUMNO);

        // Get all the alumnoList where idAlumno not equals to UPDATED_ID_ALUMNO
        defaultAlumnoShouldBeFound("idAlumno.notEquals=" + UPDATED_ID_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoIsInShouldWork() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno in DEFAULT_ID_ALUMNO or UPDATED_ID_ALUMNO
        defaultAlumnoShouldBeFound("idAlumno.in=" + DEFAULT_ID_ALUMNO + "," + UPDATED_ID_ALUMNO);

        // Get all the alumnoList where idAlumno equals to UPDATED_ID_ALUMNO
        defaultAlumnoShouldNotBeFound("idAlumno.in=" + UPDATED_ID_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno is not null
        defaultAlumnoShouldBeFound("idAlumno.specified=true");

        // Get all the alumnoList where idAlumno is null
        defaultAlumnoShouldNotBeFound("idAlumno.specified=false");
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoContainsSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno contains DEFAULT_ID_ALUMNO
        defaultAlumnoShouldBeFound("idAlumno.contains=" + DEFAULT_ID_ALUMNO);

        // Get all the alumnoList where idAlumno contains UPDATED_ID_ALUMNO
        defaultAlumnoShouldNotBeFound("idAlumno.contains=" + UPDATED_ID_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByIdAlumnoNotContainsSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where idAlumno does not contain DEFAULT_ID_ALUMNO
        defaultAlumnoShouldNotBeFound("idAlumno.doesNotContain=" + DEFAULT_ID_ALUMNO);

        // Get all the alumnoList where idAlumno does not contain UPDATED_ID_ALUMNO
        defaultAlumnoShouldBeFound("idAlumno.doesNotContain=" + UPDATED_ID_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoIsEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno equals to DEFAULT_ALUMNO
        defaultAlumnoShouldBeFound("alumno.equals=" + DEFAULT_ALUMNO);

        // Get all the alumnoList where alumno equals to UPDATED_ALUMNO
        defaultAlumnoShouldNotBeFound("alumno.equals=" + UPDATED_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno not equals to DEFAULT_ALUMNO
        defaultAlumnoShouldNotBeFound("alumno.notEquals=" + DEFAULT_ALUMNO);

        // Get all the alumnoList where alumno not equals to UPDATED_ALUMNO
        defaultAlumnoShouldBeFound("alumno.notEquals=" + UPDATED_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoIsInShouldWork() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno in DEFAULT_ALUMNO or UPDATED_ALUMNO
        defaultAlumnoShouldBeFound("alumno.in=" + DEFAULT_ALUMNO + "," + UPDATED_ALUMNO);

        // Get all the alumnoList where alumno equals to UPDATED_ALUMNO
        defaultAlumnoShouldNotBeFound("alumno.in=" + UPDATED_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno is not null
        defaultAlumnoShouldBeFound("alumno.specified=true");

        // Get all the alumnoList where alumno is null
        defaultAlumnoShouldNotBeFound("alumno.specified=false");
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoContainsSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno contains DEFAULT_ALUMNO
        defaultAlumnoShouldBeFound("alumno.contains=" + DEFAULT_ALUMNO);

        // Get all the alumnoList where alumno contains UPDATED_ALUMNO
        defaultAlumnoShouldNotBeFound("alumno.contains=" + UPDATED_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByAlumnoNotContainsSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where alumno does not contain DEFAULT_ALUMNO
        defaultAlumnoShouldNotBeFound("alumno.doesNotContain=" + DEFAULT_ALUMNO);

        // Get all the alumnoList where alumno does not contain UPDATED_ALUMNO
        defaultAlumnoShouldBeFound("alumno.doesNotContain=" + UPDATED_ALUMNO);
    }

    @Test
    @Transactional
    void getAllAlumnosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where estatus equals to DEFAULT_ESTATUS
        defaultAlumnoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the alumnoList where estatus equals to UPDATED_ESTATUS
        defaultAlumnoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllAlumnosByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where estatus not equals to DEFAULT_ESTATUS
        defaultAlumnoShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the alumnoList where estatus not equals to UPDATED_ESTATUS
        defaultAlumnoShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllAlumnosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultAlumnoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the alumnoList where estatus equals to UPDATED_ESTATUS
        defaultAlumnoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllAlumnosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList where estatus is not null
        defaultAlumnoShouldBeFound("estatus.specified=true");

        // Get all the alumnoList where estatus is null
        defaultAlumnoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAlumnosByMateriaIsEqualToSomething() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);
        Materia materia = MateriaResourceIT.createEntity(em);
        em.persist(materia);
        em.flush();
        alumno.setMateria(materia);
        alumnoRepository.saveAndFlush(alumno);
        Long materiaId = materia.getId();

        // Get all the alumnoList where materia equals to materiaId
        defaultAlumnoShouldBeFound("materiaId.equals=" + materiaId);

        // Get all the alumnoList where materia equals to (materiaId + 1)
        defaultAlumnoShouldNotBeFound("materiaId.equals=" + (materiaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlumnoShouldBeFound(String filter) throws Exception {
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAlumno").value(hasItem(DEFAULT_ID_ALUMNO)))
            .andExpect(jsonPath("$.[*].alumno").value(hasItem(DEFAULT_ALUMNO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlumnoShouldNotBeFound(String filter) throws Exception {
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlumnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAlumno() throws Exception {
        // Get the alumno
        restAlumnoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno
        Alumno updatedAlumno = alumnoRepository.findById(alumno.getId()).get();
        // Disconnect from session so that the updates on updatedAlumno are not directly saved in db
        em.detach(updatedAlumno);
        updatedAlumno.idAlumno(UPDATED_ID_ALUMNO).alumno(UPDATED_ALUMNO).estatus(UPDATED_ESTATUS);
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(updatedAlumno);

        restAlumnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alumnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getIdAlumno()).isEqualTo(UPDATED_ID_ALUMNO);
        assertThat(testAlumno.getAlumno()).isEqualTo(UPDATED_ALUMNO);
        assertThat(testAlumno.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void putNonExistingAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alumnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alumnoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlumnoWithPatch() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno using partial update
        Alumno partialUpdatedAlumno = new Alumno();
        partialUpdatedAlumno.setId(alumno.getId());

        partialUpdatedAlumno.estatus(UPDATED_ESTATUS);

        restAlumnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlumno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlumno))
            )
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getIdAlumno()).isEqualTo(DEFAULT_ID_ALUMNO);
        assertThat(testAlumno.getAlumno()).isEqualTo(DEFAULT_ALUMNO);
        assertThat(testAlumno.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void fullUpdateAlumnoWithPatch() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno using partial update
        Alumno partialUpdatedAlumno = new Alumno();
        partialUpdatedAlumno.setId(alumno.getId());

        partialUpdatedAlumno.idAlumno(UPDATED_ID_ALUMNO).alumno(UPDATED_ALUMNO).estatus(UPDATED_ESTATUS);

        restAlumnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlumno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlumno))
            )
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getIdAlumno()).isEqualTo(UPDATED_ID_ALUMNO);
        assertThat(testAlumno.getAlumno()).isEqualTo(UPDATED_ALUMNO);
        assertThat(testAlumno.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alumnoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();
        alumno.setId(count.incrementAndGet());

        // Create the Alumno
        AlumnoDTO alumnoDTO = alumnoMapper.toDto(alumno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlumnoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alumnoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        int databaseSizeBeforeDelete = alumnoRepository.findAll().size();

        // Delete the alumno
        restAlumnoMockMvc
            .perform(delete(ENTITY_API_URL_ID, alumno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Alumno;
import com.mycompany.myapp.domain.Materia;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.MateriaRepository;
import com.mycompany.myapp.service.criteria.MateriaCriteria;
import com.mycompany.myapp.service.dto.MateriaDTO;
import com.mycompany.myapp.service.mapper.MateriaMapper;
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
 * Integration tests for the {@link MateriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriaResourceIT {

    private static final String DEFAULT_ID_MATERIA = "AAAAAAAAAA";
    private static final String UPDATED_ID_MATERIA = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIA = "AAAAAAAAAA";
    private static final String UPDATED_MATERIA = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATURA = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATURA = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    private static final String ENTITY_API_URL = "/api/materias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaMapper materiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaMockMvc;

    private Materia materia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createEntity(EntityManager em) {
        Materia materia = new Materia()
            .idMateria(DEFAULT_ID_MATERIA)
            .materia(DEFAULT_MATERIA)
            .abreviatura(DEFAULT_ABREVIATURA)
            .estatus(DEFAULT_ESTATUS);
        return materia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createUpdatedEntity(EntityManager em) {
        Materia materia = new Materia()
            .idMateria(UPDATED_ID_MATERIA)
            .materia(UPDATED_MATERIA)
            .abreviatura(UPDATED_ABREVIATURA)
            .estatus(UPDATED_ESTATUS);
        return materia;
    }

    @BeforeEach
    public void initTest() {
        materia = createEntity(em);
    }

    @Test
    @Transactional
    void createMateria() throws Exception {
        int databaseSizeBeforeCreate = materiaRepository.findAll().size();
        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);
        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate + 1);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testMateria.getMateria()).isEqualTo(DEFAULT_MATERIA);
        assertThat(testMateria.getAbreviatura()).isEqualTo(DEFAULT_ABREVIATURA);
        assertThat(testMateria.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    void createMateriaWithExistingId() throws Exception {
        // Create the Materia with an existing ID
        materia.setId(1L);
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        int databaseSizeBeforeCreate = materiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdMateriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materiaRepository.findAll().size();
        // set the field null
        materia.setIdMateria(null);

        // Create the Materia, which fails.
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isBadRequest());

        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMateriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materiaRepository.findAll().size();
        // set the field null
        materia.setMateria(null);

        // Create the Materia, which fails.
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isBadRequest());

        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAbreviaturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materiaRepository.findAll().size();
        // set the field null
        materia.setAbreviatura(null);

        // Create the Materia, which fails.
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isBadRequest());

        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = materiaRepository.findAll().size();
        // set the field null
        materia.setEstatus(null);

        // Create the Materia, which fails.
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isBadRequest());

        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterias() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materia.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA)))
            .andExpect(jsonPath("$.[*].materia").value(hasItem(DEFAULT_MATERIA)))
            .andExpect(jsonPath("$.[*].abreviatura").value(hasItem(DEFAULT_ABREVIATURA)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    void getMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get the materia
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL_ID, materia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materia.getId().intValue()))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA))
            .andExpect(jsonPath("$.materia").value(DEFAULT_MATERIA))
            .andExpect(jsonPath("$.abreviatura").value(DEFAULT_ABREVIATURA))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    void getMateriasByIdFiltering() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        Long id = materia.getId();

        defaultMateriaShouldBeFound("id.equals=" + id);
        defaultMateriaShouldNotBeFound("id.notEquals=" + id);

        defaultMateriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMateriaShouldNotBeFound("id.greaterThan=" + id);

        defaultMateriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMateriaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaIsEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria equals to DEFAULT_ID_MATERIA
        defaultMateriaShouldBeFound("idMateria.equals=" + DEFAULT_ID_MATERIA);

        // Get all the materiaList where idMateria equals to UPDATED_ID_MATERIA
        defaultMateriaShouldNotBeFound("idMateria.equals=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria not equals to DEFAULT_ID_MATERIA
        defaultMateriaShouldNotBeFound("idMateria.notEquals=" + DEFAULT_ID_MATERIA);

        // Get all the materiaList where idMateria not equals to UPDATED_ID_MATERIA
        defaultMateriaShouldBeFound("idMateria.notEquals=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaIsInShouldWork() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria in DEFAULT_ID_MATERIA or UPDATED_ID_MATERIA
        defaultMateriaShouldBeFound("idMateria.in=" + DEFAULT_ID_MATERIA + "," + UPDATED_ID_MATERIA);

        // Get all the materiaList where idMateria equals to UPDATED_ID_MATERIA
        defaultMateriaShouldNotBeFound("idMateria.in=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria is not null
        defaultMateriaShouldBeFound("idMateria.specified=true");

        // Get all the materiaList where idMateria is null
        defaultMateriaShouldNotBeFound("idMateria.specified=false");
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria contains DEFAULT_ID_MATERIA
        defaultMateriaShouldBeFound("idMateria.contains=" + DEFAULT_ID_MATERIA);

        // Get all the materiaList where idMateria contains UPDATED_ID_MATERIA
        defaultMateriaShouldNotBeFound("idMateria.contains=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByIdMateriaNotContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where idMateria does not contain DEFAULT_ID_MATERIA
        defaultMateriaShouldNotBeFound("idMateria.doesNotContain=" + DEFAULT_ID_MATERIA);

        // Get all the materiaList where idMateria does not contain UPDATED_ID_MATERIA
        defaultMateriaShouldBeFound("idMateria.doesNotContain=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaIsEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia equals to DEFAULT_MATERIA
        defaultMateriaShouldBeFound("materia.equals=" + DEFAULT_MATERIA);

        // Get all the materiaList where materia equals to UPDATED_MATERIA
        defaultMateriaShouldNotBeFound("materia.equals=" + UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia not equals to DEFAULT_MATERIA
        defaultMateriaShouldNotBeFound("materia.notEquals=" + DEFAULT_MATERIA);

        // Get all the materiaList where materia not equals to UPDATED_MATERIA
        defaultMateriaShouldBeFound("materia.notEquals=" + UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaIsInShouldWork() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia in DEFAULT_MATERIA or UPDATED_MATERIA
        defaultMateriaShouldBeFound("materia.in=" + DEFAULT_MATERIA + "," + UPDATED_MATERIA);

        // Get all the materiaList where materia equals to UPDATED_MATERIA
        defaultMateriaShouldNotBeFound("materia.in=" + UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia is not null
        defaultMateriaShouldBeFound("materia.specified=true");

        // Get all the materiaList where materia is null
        defaultMateriaShouldNotBeFound("materia.specified=false");
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia contains DEFAULT_MATERIA
        defaultMateriaShouldBeFound("materia.contains=" + DEFAULT_MATERIA);

        // Get all the materiaList where materia contains UPDATED_MATERIA
        defaultMateriaShouldNotBeFound("materia.contains=" + UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByMateriaNotContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where materia does not contain DEFAULT_MATERIA
        defaultMateriaShouldNotBeFound("materia.doesNotContain=" + DEFAULT_MATERIA);

        // Get all the materiaList where materia does not contain UPDATED_MATERIA
        defaultMateriaShouldBeFound("materia.doesNotContain=" + UPDATED_MATERIA);
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura equals to DEFAULT_ABREVIATURA
        defaultMateriaShouldBeFound("abreviatura.equals=" + DEFAULT_ABREVIATURA);

        // Get all the materiaList where abreviatura equals to UPDATED_ABREVIATURA
        defaultMateriaShouldNotBeFound("abreviatura.equals=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura not equals to DEFAULT_ABREVIATURA
        defaultMateriaShouldNotBeFound("abreviatura.notEquals=" + DEFAULT_ABREVIATURA);

        // Get all the materiaList where abreviatura not equals to UPDATED_ABREVIATURA
        defaultMateriaShouldBeFound("abreviatura.notEquals=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaIsInShouldWork() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura in DEFAULT_ABREVIATURA or UPDATED_ABREVIATURA
        defaultMateriaShouldBeFound("abreviatura.in=" + DEFAULT_ABREVIATURA + "," + UPDATED_ABREVIATURA);

        // Get all the materiaList where abreviatura equals to UPDATED_ABREVIATURA
        defaultMateriaShouldNotBeFound("abreviatura.in=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura is not null
        defaultMateriaShouldBeFound("abreviatura.specified=true");

        // Get all the materiaList where abreviatura is null
        defaultMateriaShouldNotBeFound("abreviatura.specified=false");
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura contains DEFAULT_ABREVIATURA
        defaultMateriaShouldBeFound("abreviatura.contains=" + DEFAULT_ABREVIATURA);

        // Get all the materiaList where abreviatura contains UPDATED_ABREVIATURA
        defaultMateriaShouldNotBeFound("abreviatura.contains=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    void getAllMateriasByAbreviaturaNotContainsSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where abreviatura does not contain DEFAULT_ABREVIATURA
        defaultMateriaShouldNotBeFound("abreviatura.doesNotContain=" + DEFAULT_ABREVIATURA);

        // Get all the materiaList where abreviatura does not contain UPDATED_ABREVIATURA
        defaultMateriaShouldBeFound("abreviatura.doesNotContain=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    void getAllMateriasByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where estatus equals to DEFAULT_ESTATUS
        defaultMateriaShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the materiaList where estatus equals to UPDATED_ESTATUS
        defaultMateriaShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMateriasByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where estatus not equals to DEFAULT_ESTATUS
        defaultMateriaShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the materiaList where estatus not equals to UPDATED_ESTATUS
        defaultMateriaShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMateriasByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultMateriaShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the materiaList where estatus equals to UPDATED_ESTATUS
        defaultMateriaShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMateriasByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList where estatus is not null
        defaultMateriaShouldBeFound("estatus.specified=true");

        // Get all the materiaList where estatus is null
        defaultMateriaShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMateriasByAlumnoIsEqualToSomething() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);
        Alumno alumno = AlumnoResourceIT.createEntity(em);
        em.persist(alumno);
        em.flush();
        materia.addAlumno(alumno);
        materiaRepository.saveAndFlush(materia);
        Long alumnoId = alumno.getId();

        // Get all the materiaList where alumno equals to alumnoId
        defaultMateriaShouldBeFound("alumnoId.equals=" + alumnoId);

        // Get all the materiaList where alumno equals to (alumnoId + 1)
        defaultMateriaShouldNotBeFound("alumnoId.equals=" + (alumnoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMateriaShouldBeFound(String filter) throws Exception {
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materia.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA)))
            .andExpect(jsonPath("$.[*].materia").value(hasItem(DEFAULT_MATERIA)))
            .andExpect(jsonPath("$.[*].abreviatura").value(hasItem(DEFAULT_ABREVIATURA)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMateriaShouldNotBeFound(String filter) throws Exception {
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMateria() throws Exception {
        // Get the materia
        restMateriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia
        Materia updatedMateria = materiaRepository.findById(materia.getId()).get();
        // Disconnect from session so that the updates on updatedMateria are not directly saved in db
        em.detach(updatedMateria);
        updatedMateria.idMateria(UPDATED_ID_MATERIA).materia(UPDATED_MATERIA).abreviatura(UPDATED_ABREVIATURA).estatus(UPDATED_ESTATUS);
        MateriaDTO materiaDTO = materiaMapper.toDto(updatedMateria);

        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testMateria.getMateria()).isEqualTo(UPDATED_MATERIA);
        assertThat(testMateria.getAbreviatura()).isEqualTo(UPDATED_ABREVIATURA);
        assertThat(testMateria.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void putNonExistingMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMateriaWithPatch() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia using partial update
        Materia partialUpdatedMateria = new Materia();
        partialUpdatedMateria.setId(materia.getId());

        partialUpdatedMateria.abreviatura(UPDATED_ABREVIATURA).estatus(UPDATED_ESTATUS);

        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateria))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testMateria.getMateria()).isEqualTo(DEFAULT_MATERIA);
        assertThat(testMateria.getAbreviatura()).isEqualTo(UPDATED_ABREVIATURA);
        assertThat(testMateria.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void fullUpdateMateriaWithPatch() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia using partial update
        Materia partialUpdatedMateria = new Materia();
        partialUpdatedMateria.setId(materia.getId());

        partialUpdatedMateria
            .idMateria(UPDATED_ID_MATERIA)
            .materia(UPDATED_MATERIA)
            .abreviatura(UPDATED_ABREVIATURA)
            .estatus(UPDATED_ESTATUS);

        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateria))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testMateria.getMateria()).isEqualTo(UPDATED_MATERIA);
        assertThat(testMateria.getAbreviatura()).isEqualTo(UPDATED_ABREVIATURA);
        assertThat(testMateria.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void patchNonExistingMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // Create the Materia
        MateriaDTO materiaDTO = materiaMapper.toDto(materia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(materiaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeDelete = materiaRepository.findAll().size();

        // Delete the materia
        restMateriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, materia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

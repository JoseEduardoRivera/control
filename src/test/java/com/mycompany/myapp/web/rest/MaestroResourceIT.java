package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Maestro;
import com.mycompany.myapp.domain.enumeration.EstatusRegistro;
import com.mycompany.myapp.repository.MaestroRepository;
import com.mycompany.myapp.service.criteria.MaestroCriteria;
import com.mycompany.myapp.service.dto.MaestroDTO;
import com.mycompany.myapp.service.mapper.MaestroMapper;
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
 * Integration tests for the {@link MaestroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaestroResourceIT {

    private static final String DEFAULT_ID_MAESTRO = "AAAAAAAAAA";
    private static final String UPDATED_ID_MAESTRO = "BBBBBBBBBB";

    private static final String DEFAULT_MAESTRO = "AAAAAAAAAA";
    private static final String UPDATED_MAESTRO = "BBBBBBBBBB";

    private static final EstatusRegistro DEFAULT_ESTATUS = EstatusRegistro.ACTIVO;
    private static final EstatusRegistro UPDATED_ESTATUS = EstatusRegistro.DESACTIVADO;

    private static final String ENTITY_API_URL = "/api/maestros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaestroRepository maestroRepository;

    @Autowired
    private MaestroMapper maestroMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaestroMockMvc;

    private Maestro maestro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maestro createEntity(EntityManager em) {
        Maestro maestro = new Maestro().idMaestro(DEFAULT_ID_MAESTRO).maestro(DEFAULT_MAESTRO).estatus(DEFAULT_ESTATUS);
        return maestro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maestro createUpdatedEntity(EntityManager em) {
        Maestro maestro = new Maestro().idMaestro(UPDATED_ID_MAESTRO).maestro(UPDATED_MAESTRO).estatus(UPDATED_ESTATUS);
        return maestro;
    }

    @BeforeEach
    public void initTest() {
        maestro = createEntity(em);
    }

    @Test
    @Transactional
    void createMaestro() throws Exception {
        int databaseSizeBeforeCreate = maestroRepository.findAll().size();
        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);
        restMaestroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isCreated());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeCreate + 1);
        Maestro testMaestro = maestroList.get(maestroList.size() - 1);
        assertThat(testMaestro.getIdMaestro()).isEqualTo(DEFAULT_ID_MAESTRO);
        assertThat(testMaestro.getMaestro()).isEqualTo(DEFAULT_MAESTRO);
        assertThat(testMaestro.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    void createMaestroWithExistingId() throws Exception {
        // Create the Maestro with an existing ID
        maestro.setId(1L);
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        int databaseSizeBeforeCreate = maestroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaestroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdMaestroIsRequired() throws Exception {
        int databaseSizeBeforeTest = maestroRepository.findAll().size();
        // set the field null
        maestro.setIdMaestro(null);

        // Create the Maestro, which fails.
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        restMaestroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isBadRequest());

        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaestroIsRequired() throws Exception {
        int databaseSizeBeforeTest = maestroRepository.findAll().size();
        // set the field null
        maestro.setMaestro(null);

        // Create the Maestro, which fails.
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        restMaestroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isBadRequest());

        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = maestroRepository.findAll().size();
        // set the field null
        maestro.setEstatus(null);

        // Create the Maestro, which fails.
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        restMaestroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isBadRequest());

        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaestros() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maestro.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMaestro").value(hasItem(DEFAULT_ID_MAESTRO)))
            .andExpect(jsonPath("$.[*].maestro").value(hasItem(DEFAULT_MAESTRO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }

    @Test
    @Transactional
    void getMaestro() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get the maestro
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL_ID, maestro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maestro.getId().intValue()))
            .andExpect(jsonPath("$.idMaestro").value(DEFAULT_ID_MAESTRO))
            .andExpect(jsonPath("$.maestro").value(DEFAULT_MAESTRO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    void getMaestrosByIdFiltering() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        Long id = maestro.getId();

        defaultMaestroShouldBeFound("id.equals=" + id);
        defaultMaestroShouldNotBeFound("id.notEquals=" + id);

        defaultMaestroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaestroShouldNotBeFound("id.greaterThan=" + id);

        defaultMaestroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaestroShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroIsEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro equals to DEFAULT_ID_MAESTRO
        defaultMaestroShouldBeFound("idMaestro.equals=" + DEFAULT_ID_MAESTRO);

        // Get all the maestroList where idMaestro equals to UPDATED_ID_MAESTRO
        defaultMaestroShouldNotBeFound("idMaestro.equals=" + UPDATED_ID_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro not equals to DEFAULT_ID_MAESTRO
        defaultMaestroShouldNotBeFound("idMaestro.notEquals=" + DEFAULT_ID_MAESTRO);

        // Get all the maestroList where idMaestro not equals to UPDATED_ID_MAESTRO
        defaultMaestroShouldBeFound("idMaestro.notEquals=" + UPDATED_ID_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroIsInShouldWork() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro in DEFAULT_ID_MAESTRO or UPDATED_ID_MAESTRO
        defaultMaestroShouldBeFound("idMaestro.in=" + DEFAULT_ID_MAESTRO + "," + UPDATED_ID_MAESTRO);

        // Get all the maestroList where idMaestro equals to UPDATED_ID_MAESTRO
        defaultMaestroShouldNotBeFound("idMaestro.in=" + UPDATED_ID_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroIsNullOrNotNull() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro is not null
        defaultMaestroShouldBeFound("idMaestro.specified=true");

        // Get all the maestroList where idMaestro is null
        defaultMaestroShouldNotBeFound("idMaestro.specified=false");
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroContainsSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro contains DEFAULT_ID_MAESTRO
        defaultMaestroShouldBeFound("idMaestro.contains=" + DEFAULT_ID_MAESTRO);

        // Get all the maestroList where idMaestro contains UPDATED_ID_MAESTRO
        defaultMaestroShouldNotBeFound("idMaestro.contains=" + UPDATED_ID_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByIdMaestroNotContainsSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where idMaestro does not contain DEFAULT_ID_MAESTRO
        defaultMaestroShouldNotBeFound("idMaestro.doesNotContain=" + DEFAULT_ID_MAESTRO);

        // Get all the maestroList where idMaestro does not contain UPDATED_ID_MAESTRO
        defaultMaestroShouldBeFound("idMaestro.doesNotContain=" + UPDATED_ID_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroIsEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro equals to DEFAULT_MAESTRO
        defaultMaestroShouldBeFound("maestro.equals=" + DEFAULT_MAESTRO);

        // Get all the maestroList where maestro equals to UPDATED_MAESTRO
        defaultMaestroShouldNotBeFound("maestro.equals=" + UPDATED_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro not equals to DEFAULT_MAESTRO
        defaultMaestroShouldNotBeFound("maestro.notEquals=" + DEFAULT_MAESTRO);

        // Get all the maestroList where maestro not equals to UPDATED_MAESTRO
        defaultMaestroShouldBeFound("maestro.notEquals=" + UPDATED_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroIsInShouldWork() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro in DEFAULT_MAESTRO or UPDATED_MAESTRO
        defaultMaestroShouldBeFound("maestro.in=" + DEFAULT_MAESTRO + "," + UPDATED_MAESTRO);

        // Get all the maestroList where maestro equals to UPDATED_MAESTRO
        defaultMaestroShouldNotBeFound("maestro.in=" + UPDATED_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroIsNullOrNotNull() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro is not null
        defaultMaestroShouldBeFound("maestro.specified=true");

        // Get all the maestroList where maestro is null
        defaultMaestroShouldNotBeFound("maestro.specified=false");
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroContainsSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro contains DEFAULT_MAESTRO
        defaultMaestroShouldBeFound("maestro.contains=" + DEFAULT_MAESTRO);

        // Get all the maestroList where maestro contains UPDATED_MAESTRO
        defaultMaestroShouldNotBeFound("maestro.contains=" + UPDATED_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByMaestroNotContainsSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where maestro does not contain DEFAULT_MAESTRO
        defaultMaestroShouldNotBeFound("maestro.doesNotContain=" + DEFAULT_MAESTRO);

        // Get all the maestroList where maestro does not contain UPDATED_MAESTRO
        defaultMaestroShouldBeFound("maestro.doesNotContain=" + UPDATED_MAESTRO);
    }

    @Test
    @Transactional
    void getAllMaestrosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where estatus equals to DEFAULT_ESTATUS
        defaultMaestroShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the maestroList where estatus equals to UPDATED_ESTATUS
        defaultMaestroShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMaestrosByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where estatus not equals to DEFAULT_ESTATUS
        defaultMaestroShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the maestroList where estatus not equals to UPDATED_ESTATUS
        defaultMaestroShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMaestrosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultMaestroShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the maestroList where estatus equals to UPDATED_ESTATUS
        defaultMaestroShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllMaestrosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        // Get all the maestroList where estatus is not null
        defaultMaestroShouldBeFound("estatus.specified=true");

        // Get all the maestroList where estatus is null
        defaultMaestroShouldNotBeFound("estatus.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaestroShouldBeFound(String filter) throws Exception {
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maestro.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMaestro").value(hasItem(DEFAULT_ID_MAESTRO)))
            .andExpect(jsonPath("$.[*].maestro").value(hasItem(DEFAULT_MAESTRO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));

        // Check, that the count call also returns 1
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaestroShouldNotBeFound(String filter) throws Exception {
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaestroMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaestro() throws Exception {
        // Get the maestro
        restMaestroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMaestro() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();

        // Update the maestro
        Maestro updatedMaestro = maestroRepository.findById(maestro.getId()).get();
        // Disconnect from session so that the updates on updatedMaestro are not directly saved in db
        em.detach(updatedMaestro);
        updatedMaestro.idMaestro(UPDATED_ID_MAESTRO).maestro(UPDATED_MAESTRO).estatus(UPDATED_ESTATUS);
        MaestroDTO maestroDTO = maestroMapper.toDto(updatedMaestro);

        restMaestroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maestroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isOk());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
        Maestro testMaestro = maestroList.get(maestroList.size() - 1);
        assertThat(testMaestro.getIdMaestro()).isEqualTo(UPDATED_ID_MAESTRO);
        assertThat(testMaestro.getMaestro()).isEqualTo(UPDATED_MAESTRO);
        assertThat(testMaestro.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void putNonExistingMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maestroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maestroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaestroWithPatch() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();

        // Update the maestro using partial update
        Maestro partialUpdatedMaestro = new Maestro();
        partialUpdatedMaestro.setId(maestro.getId());

        partialUpdatedMaestro.estatus(UPDATED_ESTATUS);

        restMaestroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaestro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaestro))
            )
            .andExpect(status().isOk());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
        Maestro testMaestro = maestroList.get(maestroList.size() - 1);
        assertThat(testMaestro.getIdMaestro()).isEqualTo(DEFAULT_ID_MAESTRO);
        assertThat(testMaestro.getMaestro()).isEqualTo(DEFAULT_MAESTRO);
        assertThat(testMaestro.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void fullUpdateMaestroWithPatch() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();

        // Update the maestro using partial update
        Maestro partialUpdatedMaestro = new Maestro();
        partialUpdatedMaestro.setId(maestro.getId());

        partialUpdatedMaestro.idMaestro(UPDATED_ID_MAESTRO).maestro(UPDATED_MAESTRO).estatus(UPDATED_ESTATUS);

        restMaestroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaestro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaestro))
            )
            .andExpect(status().isOk());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
        Maestro testMaestro = maestroList.get(maestroList.size() - 1);
        assertThat(testMaestro.getIdMaestro()).isEqualTo(UPDATED_ID_MAESTRO);
        assertThat(testMaestro.getMaestro()).isEqualTo(UPDATED_MAESTRO);
        assertThat(testMaestro.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void patchNonExistingMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maestroDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaestro() throws Exception {
        int databaseSizeBeforeUpdate = maestroRepository.findAll().size();
        maestro.setId(count.incrementAndGet());

        // Create the Maestro
        MaestroDTO maestroDTO = maestroMapper.toDto(maestro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaestroMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(maestroDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Maestro in the database
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaestro() throws Exception {
        // Initialize the database
        maestroRepository.saveAndFlush(maestro);

        int databaseSizeBeforeDelete = maestroRepository.findAll().size();

        // Delete the maestro
        restMaestroMockMvc
            .perform(delete(ENTITY_API_URL_ID, maestro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Maestro> maestroList = maestroRepository.findAll();
        assertThat(maestroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

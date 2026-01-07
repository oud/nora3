package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CreancierAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Creancier;
import com.mycompany.myapp.repository.CreancierRepository;
import com.mycompany.myapp.service.dto.CreancierDTO;
import com.mycompany.myapp.service.mapper.CreancierMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CreancierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreancierResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String DEFAULT_NIR = "AAAAAAAAAA";
    private static final String UPDATED_NIR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLE_NIR = 1;
    private static final Integer UPDATED_CLE_NIR = 2;

    private static final Integer DEFAULT_NUM_ALLOC_CRISTAL = 1;
    private static final Integer UPDATED_NUM_ALLOC_CRISTAL = 2;

    private static final Integer DEFAULT_NUM_PERSONNE_CRISTAL = 1;
    private static final Integer UPDATED_NUM_PERSONNE_CRISTAL = 2;

    private static final String DEFAULT_CODE_ORGANISME_CRISTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ORGANISME_CRISTAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SITUATION_FAMILIALE_DEBUT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SITUATION_FAMILIALE_DEBUT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_SITUATION_FAMILIALE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SITUATION_FAMILIALE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NATIONALITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NATIONALITE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_USER_CREATION = "AAAAAAAAAA";
    private static final String UPDATED_USER_CREATION = "BBBBBBBBBB";

    private static final LocalTime DEFAULT_CREATION_DATE = LocalTime.NOON;
    private static final LocalTime UPDATED_CREATION_DATE = LocalTime.MAX.withNano(0);

    private static final String DEFAULT_USER_MAJ = "AAAAAAAAAA";
    private static final String UPDATED_USER_MAJ = "BBBBBBBBBB";

    private static final LocalTime DEFAULT_MAJ_DATE = LocalTime.NOON;
    private static final LocalTime UPDATED_MAJ_DATE = LocalTime.MAX.withNano(0);

    private static final Integer DEFAULT_NUM_MAJ = 1;
    private static final Integer UPDATED_NUM_MAJ = 2;

    private static final String ENTITY_API_URL = "/api/creanciers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CreancierRepository creancierRepository;

    @Autowired
    private CreancierMapper creancierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreancierMockMvc;

    private Creancier creancier;

    private Creancier insertedCreancier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creancier createEntity() {
        return new Creancier()
            .nir(DEFAULT_NIR)
            .cleNir(DEFAULT_CLE_NIR)
            .numAllocCristal(DEFAULT_NUM_ALLOC_CRISTAL)
            .numPersonneCristal(DEFAULT_NUM_PERSONNE_CRISTAL)
            .codeOrganismeCristal(DEFAULT_CODE_ORGANISME_CRISTAL)
            .situationFamilialeDebutDate(DEFAULT_SITUATION_FAMILIALE_DEBUT_DATE)
            .codeSituationFamiliale(DEFAULT_CODE_SITUATION_FAMILIALE)
            .codeNationalite(DEFAULT_CODE_NATIONALITE)
            .codeAgent(DEFAULT_CODE_AGENT)
            .userCreation(DEFAULT_USER_CREATION)
            .creationDate(DEFAULT_CREATION_DATE)
            .userMaj(DEFAULT_USER_MAJ)
            .majDate(DEFAULT_MAJ_DATE)
            .numMaj(DEFAULT_NUM_MAJ);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creancier createUpdatedEntity() {
        return new Creancier()
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .numPersonneCristal(UPDATED_NUM_PERSONNE_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .situationFamilialeDebutDate(UPDATED_SITUATION_FAMILIALE_DEBUT_DATE)
            .codeSituationFamiliale(UPDATED_CODE_SITUATION_FAMILIALE)
            .codeNationalite(UPDATED_CODE_NATIONALITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        creancier = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCreancier != null) {
            creancierRepository.delete(insertedCreancier);
            insertedCreancier = null;
        }
    }

    @Test
    @Transactional
    void createCreancier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);
        var returnedCreancierDTO = om.readValue(
            restCreancierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(creancierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CreancierDTO.class
        );

        // Validate the Creancier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCreancier = creancierMapper.toEntity(returnedCreancierDTO);
        assertCreancierUpdatableFieldsEquals(returnedCreancier, getPersistedCreancier(returnedCreancier));

        insertedCreancier = returnedCreancier;
    }

    @Test
    @Transactional
    void createCreancierWithExistingId() throws Exception {
        // Create the Creancier with an existing ID
        creancier.setId(1L);
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreancierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(creancierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreanciers() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        // Get all the creancierList
        restCreancierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creancier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nir").value(hasItem(DEFAULT_NIR)))
            .andExpect(jsonPath("$.[*].cleNir").value(hasItem(DEFAULT_CLE_NIR)))
            .andExpect(jsonPath("$.[*].numAllocCristal").value(hasItem(DEFAULT_NUM_ALLOC_CRISTAL)))
            .andExpect(jsonPath("$.[*].numPersonneCristal").value(hasItem(DEFAULT_NUM_PERSONNE_CRISTAL)))
            .andExpect(jsonPath("$.[*].codeOrganismeCristal").value(hasItem(DEFAULT_CODE_ORGANISME_CRISTAL)))
            .andExpect(jsonPath("$.[*].situationFamilialeDebutDate").value(hasItem(DEFAULT_SITUATION_FAMILIALE_DEBUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeSituationFamiliale").value(hasItem(DEFAULT_CODE_SITUATION_FAMILIALE)))
            .andExpect(jsonPath("$.[*].codeNationalite").value(hasItem(DEFAULT_CODE_NATIONALITE)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getCreancier() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        // Get the creancier
        restCreancierMockMvc
            .perform(get(ENTITY_API_URL_ID, creancier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creancier.getId().intValue()))
            .andExpect(jsonPath("$.nir").value(DEFAULT_NIR))
            .andExpect(jsonPath("$.cleNir").value(DEFAULT_CLE_NIR))
            .andExpect(jsonPath("$.numAllocCristal").value(DEFAULT_NUM_ALLOC_CRISTAL))
            .andExpect(jsonPath("$.numPersonneCristal").value(DEFAULT_NUM_PERSONNE_CRISTAL))
            .andExpect(jsonPath("$.codeOrganismeCristal").value(DEFAULT_CODE_ORGANISME_CRISTAL))
            .andExpect(jsonPath("$.situationFamilialeDebutDate").value(DEFAULT_SITUATION_FAMILIALE_DEBUT_DATE.toString()))
            .andExpect(jsonPath("$.codeSituationFamiliale").value(DEFAULT_CODE_SITUATION_FAMILIALE))
            .andExpect(jsonPath("$.codeNationalite").value(DEFAULT_CODE_NATIONALITE))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingCreancier() throws Exception {
        // Get the creancier
        restCreancierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreancier() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the creancier
        Creancier updatedCreancier = creancierRepository.findById(creancier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCreancier are not directly saved in db
        em.detach(updatedCreancier);
        updatedCreancier
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .numPersonneCristal(UPDATED_NUM_PERSONNE_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .situationFamilialeDebutDate(UPDATED_SITUATION_FAMILIALE_DEBUT_DATE)
            .codeSituationFamiliale(UPDATED_CODE_SITUATION_FAMILIALE)
            .codeNationalite(UPDATED_CODE_NATIONALITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        CreancierDTO creancierDTO = creancierMapper.toDto(updatedCreancier);

        restCreancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creancierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(creancierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCreancierToMatchAllProperties(updatedCreancier);
    }

    @Test
    @Transactional
    void putNonExistingCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creancierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(creancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(creancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(creancierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreancierWithPatch() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the creancier using partial update
        Creancier partialUpdatedCreancier = new Creancier();
        partialUpdatedCreancier.setId(creancier.getId());

        partialUpdatedCreancier
            .nir(UPDATED_NIR)
            .numPersonneCristal(UPDATED_NUM_PERSONNE_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .situationFamilialeDebutDate(UPDATED_SITUATION_FAMILIALE_DEBUT_DATE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restCreancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCreancier))
            )
            .andExpect(status().isOk());

        // Validate the Creancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCreancierUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCreancier, creancier),
            getPersistedCreancier(creancier)
        );
    }

    @Test
    @Transactional
    void fullUpdateCreancierWithPatch() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the creancier using partial update
        Creancier partialUpdatedCreancier = new Creancier();
        partialUpdatedCreancier.setId(creancier.getId());

        partialUpdatedCreancier
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .numPersonneCristal(UPDATED_NUM_PERSONNE_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .situationFamilialeDebutDate(UPDATED_SITUATION_FAMILIALE_DEBUT_DATE)
            .codeSituationFamiliale(UPDATED_CODE_SITUATION_FAMILIALE)
            .codeNationalite(UPDATED_CODE_NATIONALITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restCreancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreancier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCreancier))
            )
            .andExpect(status().isOk());

        // Validate the Creancier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCreancierUpdatableFieldsEquals(partialUpdatedCreancier, getPersistedCreancier(partialUpdatedCreancier));
    }

    @Test
    @Transactional
    void patchNonExistingCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creancierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(creancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(creancierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreancier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        creancier.setId(longCount.incrementAndGet());

        // Create the Creancier
        CreancierDTO creancierDTO = creancierMapper.toDto(creancier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreancierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(creancierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Creancier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreancier() throws Exception {
        // Initialize the database
        insertedCreancier = creancierRepository.saveAndFlush(creancier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the creancier
        restCreancierMockMvc
            .perform(delete(ENTITY_API_URL_ID, creancier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return creancierRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Creancier getPersistedCreancier(Creancier creancier) {
        return creancierRepository.findById(creancier.getId()).orElseThrow();
    }

    protected void assertPersistedCreancierToMatchAllProperties(Creancier expectedCreancier) {
        assertCreancierAllPropertiesEquals(expectedCreancier, getPersistedCreancier(expectedCreancier));
    }

    protected void assertPersistedCreancierToMatchUpdatableProperties(Creancier expectedCreancier) {
        assertCreancierAllUpdatablePropertiesEquals(expectedCreancier, getPersistedCreancier(expectedCreancier));
    }
}

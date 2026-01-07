package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EnfantAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Enfant;
import com.mycompany.myapp.repository.EnfantRepository;
import com.mycompany.myapp.service.dto.EnfantDTO;
import com.mycompany.myapp.service.mapper.EnfantMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalTime;
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
 * Integration tests for the {@link EnfantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnfantResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String DEFAULT_NIR = "AAAAAAAAAA";
    private static final String UPDATED_NIR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLE_NIR = 1;
    private static final Integer UPDATED_CLE_NIR = 2;

    private static final Integer DEFAULT_NUM_PERSONNE_GAIA = 1;
    private static final Integer UPDATED_NUM_PERSONNE_GAIA = 2;

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

    private static final String ENTITY_API_URL = "/api/enfants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private EnfantMapper enfantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnfantMockMvc;

    private Enfant enfant;

    private Enfant insertedEnfant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createEntity() {
        return new Enfant()
            .nir(DEFAULT_NIR)
            .cleNir(DEFAULT_CLE_NIR)
            .numPersonneGaia(DEFAULT_NUM_PERSONNE_GAIA)
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
    public static Enfant createUpdatedEntity() {
        return new Enfant()
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numPersonneGaia(UPDATED_NUM_PERSONNE_GAIA)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        enfant = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEnfant != null) {
            enfantRepository.delete(insertedEnfant);
            insertedEnfant = null;
        }
    }

    @Test
    @Transactional
    void createEnfant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);
        var returnedEnfantDTO = om.readValue(
            restEnfantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enfantDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EnfantDTO.class
        );

        // Validate the Enfant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEnfant = enfantMapper.toEntity(returnedEnfantDTO);
        assertEnfantUpdatableFieldsEquals(returnedEnfant, getPersistedEnfant(returnedEnfant));

        insertedEnfant = returnedEnfant;
    }

    @Test
    @Transactional
    void createEnfantWithExistingId() throws Exception {
        // Create the Enfant with an existing ID
        enfant.setId(1L);
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enfantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnfants() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        // Get all the enfantList
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nir").value(hasItem(DEFAULT_NIR)))
            .andExpect(jsonPath("$.[*].cleNir").value(hasItem(DEFAULT_CLE_NIR)))
            .andExpect(jsonPath("$.[*].numPersonneGaia").value(hasItem(DEFAULT_NUM_PERSONNE_GAIA)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getEnfant() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        // Get the enfant
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL_ID, enfant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enfant.getId().intValue()))
            .andExpect(jsonPath("$.nir").value(DEFAULT_NIR))
            .andExpect(jsonPath("$.cleNir").value(DEFAULT_CLE_NIR))
            .andExpect(jsonPath("$.numPersonneGaia").value(DEFAULT_NUM_PERSONNE_GAIA))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingEnfant() throws Exception {
        // Get the enfant
        restEnfantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnfant() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enfant
        Enfant updatedEnfant = enfantRepository.findById(enfant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnfant are not directly saved in db
        em.detach(updatedEnfant);
        updatedEnfant
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numPersonneGaia(UPDATED_NUM_PERSONNE_GAIA)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        EnfantDTO enfantDTO = enfantMapper.toDto(updatedEnfant);

        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enfantDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enfantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnfantToMatchAllProperties(updatedEnfant);
    }

    @Test
    @Transactional
    void putNonExistingEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enfantDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enfantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enfantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enfantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .numPersonneGaia(UPDATED_NUM_PERSONNE_GAIA)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnfantUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEnfant, enfant), getPersistedEnfant(enfant));
    }

    @Test
    @Transactional
    void fullUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numPersonneGaia(UPDATED_NUM_PERSONNE_GAIA)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnfantUpdatableFieldsEquals(partialUpdatedEnfant, getPersistedEnfant(partialUpdatedEnfant));
    }

    @Test
    @Transactional
    void patchNonExistingEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enfantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enfantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enfantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnfant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enfant.setId(longCount.incrementAndGet());

        // Create the Enfant
        EnfantDTO enfantDTO = enfantMapper.toDto(enfant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enfantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnfant() throws Exception {
        // Initialize the database
        insertedEnfant = enfantRepository.saveAndFlush(enfant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enfant
        restEnfantMockMvc
            .perform(delete(ENTITY_API_URL_ID, enfant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enfantRepository.count();
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

    protected Enfant getPersistedEnfant(Enfant enfant) {
        return enfantRepository.findById(enfant.getId()).orElseThrow();
    }

    protected void assertPersistedEnfantToMatchAllProperties(Enfant expectedEnfant) {
        assertEnfantAllPropertiesEquals(expectedEnfant, getPersistedEnfant(expectedEnfant));
    }

    protected void assertPersistedEnfantToMatchUpdatableProperties(Enfant expectedEnfant) {
        assertEnfantAllUpdatablePropertiesEquals(expectedEnfant, getPersistedEnfant(expectedEnfant));
    }
}

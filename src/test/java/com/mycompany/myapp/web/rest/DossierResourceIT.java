package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DossierAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.dto.DossierDTO;
import com.mycompany.myapp.service.mapper.DossierMapper;
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
 * Integration tests for the {@link DossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DossierResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String DEFAULT_NUM_DOSSIER_NOR = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DOSSIER_NOR = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_DOSSIER_GAIA = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DOSSIER_GAIA = "BBBBBBBBBB";

    private static final LocalTime DEFAULT_RECEPTION_DATE_NOR = LocalTime.NOON;
    private static final LocalTime UPDATED_RECEPTION_DATE_NOR = LocalTime.MAX.withNano(0);

    private static final LocalTime DEFAULT_VALIDATION_DATE_NOR = LocalTime.NOON;
    private static final LocalTime UPDATED_VALIDATION_DATE_NOR = LocalTime.MAX.withNano(0);

    private static final String DEFAULT_CODE_ORGANISME = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ORGANISME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private DossierMapper dossierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    private Dossier insertedDossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createEntity() {
        return new Dossier()
            .numDossierNor(DEFAULT_NUM_DOSSIER_NOR)
            .numDossierGaia(DEFAULT_NUM_DOSSIER_GAIA)
            .receptionDateNor(DEFAULT_RECEPTION_DATE_NOR)
            .validationDateNor(DEFAULT_VALIDATION_DATE_NOR)
            .codeOrganisme(DEFAULT_CODE_ORGANISME)
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
    public static Dossier createUpdatedEntity() {
        return new Dossier()
            .numDossierNor(UPDATED_NUM_DOSSIER_NOR)
            .numDossierGaia(UPDATED_NUM_DOSSIER_GAIA)
            .receptionDateNor(UPDATED_RECEPTION_DATE_NOR)
            .validationDateNor(UPDATED_VALIDATION_DATE_NOR)
            .codeOrganisme(UPDATED_CODE_ORGANISME)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        dossier = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDossier != null) {
            dossierRepository.delete(insertedDossier);
            insertedDossier = null;
        }
    }

    @Test
    @Transactional
    void createDossier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);
        var returnedDossierDTO = om.readValue(
            restDossierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DossierDTO.class
        );

        // Validate the Dossier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDossier = dossierMapper.toEntity(returnedDossierDTO);
        assertDossierUpdatableFieldsEquals(returnedDossier, getPersistedDossier(returnedDossier));

        insertedDossier = returnedDossier;
    }

    @Test
    @Transactional
    void createDossierWithExistingId() throws Exception {
        // Create the Dossier with an existing ID
        dossier.setId(1L);
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDossiers() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].numDossierNor").value(hasItem(DEFAULT_NUM_DOSSIER_NOR)))
            .andExpect(jsonPath("$.[*].numDossierGaia").value(hasItem(DEFAULT_NUM_DOSSIER_GAIA)))
            .andExpect(jsonPath("$.[*].receptionDateNor").value(hasItem(DEFAULT_RECEPTION_DATE_NOR.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].validationDateNor").value(hasItem(DEFAULT_VALIDATION_DATE_NOR.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].codeOrganisme").value(hasItem(DEFAULT_CODE_ORGANISME)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.numDossierNor").value(DEFAULT_NUM_DOSSIER_NOR))
            .andExpect(jsonPath("$.numDossierGaia").value(DEFAULT_NUM_DOSSIER_GAIA))
            .andExpect(jsonPath("$.receptionDateNor").value(DEFAULT_RECEPTION_DATE_NOR.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.validationDateNor").value(DEFAULT_VALIDATION_DATE_NOR.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.codeOrganisme").value(DEFAULT_CODE_ORGANISME))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findById(dossier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .numDossierNor(UPDATED_NUM_DOSSIER_NOR)
            .numDossierGaia(UPDATED_NUM_DOSSIER_GAIA)
            .receptionDateNor(UPDATED_RECEPTION_DATE_NOR)
            .validationDateNor(UPDATED_VALIDATION_DATE_NOR)
            .codeOrganisme(UPDATED_CODE_ORGANISME)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        DossierDTO dossierDTO = dossierMapper.toDto(updatedDossier);

        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDossierToMatchAllProperties(updatedDossier);
    }

    @Test
    @Transactional
    void putNonExistingDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dossierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .numDossierGaia(UPDATED_NUM_DOSSIER_GAIA)
            .receptionDateNor(UPDATED_RECEPTION_DATE_NOR)
            .validationDateNor(UPDATED_VALIDATION_DATE_NOR)
            .codeAgent(UPDATED_CODE_AGENT)
            .creationDate(UPDATED_CREATION_DATE)
            .majDate(UPDATED_MAJ_DATE);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDossierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDossier, dossier), getPersistedDossier(dossier));
    }

    @Test
    @Transactional
    void fullUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .numDossierNor(UPDATED_NUM_DOSSIER_NOR)
            .numDossierGaia(UPDATED_NUM_DOSSIER_GAIA)
            .receptionDateNor(UPDATED_RECEPTION_DATE_NOR)
            .validationDateNor(UPDATED_VALIDATION_DATE_NOR)
            .codeOrganisme(UPDATED_CODE_ORGANISME)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDossierUpdatableFieldsEquals(partialUpdatedDossier, getPersistedDossier(partialUpdatedDossier));
    }

    @Test
    @Transactional
    void patchNonExistingDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dossier.setId(longCount.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dossierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossier() throws Exception {
        // Initialize the database
        insertedDossier = dossierRepository.saveAndFlush(dossier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dossier
        restDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dossierRepository.count();
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

    protected Dossier getPersistedDossier(Dossier dossier) {
        return dossierRepository.findById(dossier.getId()).orElseThrow();
    }

    protected void assertPersistedDossierToMatchAllProperties(Dossier expectedDossier) {
        assertDossierAllPropertiesEquals(expectedDossier, getPersistedDossier(expectedDossier));
    }

    protected void assertPersistedDossierToMatchUpdatableProperties(Dossier expectedDossier) {
        assertDossierAllUpdatablePropertiesEquals(expectedDossier, getPersistedDossier(expectedDossier));
    }
}

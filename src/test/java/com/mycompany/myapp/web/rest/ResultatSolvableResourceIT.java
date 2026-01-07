package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ResultatSolvableAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ResultatSolvable;
import com.mycompany.myapp.repository.ResultatSolvableRepository;
import com.mycompany.myapp.service.dto.ResultatSolvableDTO;
import com.mycompany.myapp.service.mapper.ResultatSolvableMapper;
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
 * Integration tests for the {@link ResultatSolvableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultatSolvableResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalDate DEFAULT_MOIS_SOLVABILITE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MOIS_SOLVABILITE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_ETAT_SOLVABILITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ETAT_SOLVABILITE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/resultat-solvables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResultatSolvableRepository resultatSolvableRepository;

    @Autowired
    private ResultatSolvableMapper resultatSolvableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultatSolvableMockMvc;

    private ResultatSolvable resultatSolvable;

    private ResultatSolvable insertedResultatSolvable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultatSolvable createEntity() {
        return new ResultatSolvable()
            .moisSolvabiliteDate(DEFAULT_MOIS_SOLVABILITE_DATE)
            .codeEtatSolvabilite(DEFAULT_CODE_ETAT_SOLVABILITE)
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
    public static ResultatSolvable createUpdatedEntity() {
        return new ResultatSolvable()
            .moisSolvabiliteDate(UPDATED_MOIS_SOLVABILITE_DATE)
            .codeEtatSolvabilite(UPDATED_CODE_ETAT_SOLVABILITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        resultatSolvable = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedResultatSolvable != null) {
            resultatSolvableRepository.delete(insertedResultatSolvable);
            insertedResultatSolvable = null;
        }
    }

    @Test
    @Transactional
    void createResultatSolvable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);
        var returnedResultatSolvableDTO = om.readValue(
            restResultatSolvableMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultatSolvableDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ResultatSolvableDTO.class
        );

        // Validate the ResultatSolvable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedResultatSolvable = resultatSolvableMapper.toEntity(returnedResultatSolvableDTO);
        assertResultatSolvableUpdatableFieldsEquals(returnedResultatSolvable, getPersistedResultatSolvable(returnedResultatSolvable));

        insertedResultatSolvable = returnedResultatSolvable;
    }

    @Test
    @Transactional
    void createResultatSolvableWithExistingId() throws Exception {
        // Create the ResultatSolvable with an existing ID
        resultatSolvable.setId(1L);
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatSolvableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultatSolvableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResultatSolvables() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        // Get all the resultatSolvableList
        restResultatSolvableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultatSolvable.getId().intValue())))
            .andExpect(jsonPath("$.[*].moisSolvabiliteDate").value(hasItem(DEFAULT_MOIS_SOLVABILITE_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeEtatSolvabilite").value(hasItem(DEFAULT_CODE_ETAT_SOLVABILITE)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getResultatSolvable() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        // Get the resultatSolvable
        restResultatSolvableMockMvc
            .perform(get(ENTITY_API_URL_ID, resultatSolvable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultatSolvable.getId().intValue()))
            .andExpect(jsonPath("$.moisSolvabiliteDate").value(DEFAULT_MOIS_SOLVABILITE_DATE.toString()))
            .andExpect(jsonPath("$.codeEtatSolvabilite").value(DEFAULT_CODE_ETAT_SOLVABILITE))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingResultatSolvable() throws Exception {
        // Get the resultatSolvable
        restResultatSolvableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResultatSolvable() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultatSolvable
        ResultatSolvable updatedResultatSolvable = resultatSolvableRepository.findById(resultatSolvable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResultatSolvable are not directly saved in db
        em.detach(updatedResultatSolvable);
        updatedResultatSolvable
            .moisSolvabiliteDate(UPDATED_MOIS_SOLVABILITE_DATE)
            .codeEtatSolvabilite(UPDATED_CODE_ETAT_SOLVABILITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(updatedResultatSolvable);

        restResultatSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultatSolvableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultatSolvableDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedResultatSolvableToMatchAllProperties(updatedResultatSolvable);
    }

    @Test
    @Transactional
    void putNonExistingResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultatSolvableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultatSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(resultatSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultatSolvableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultatSolvableWithPatch() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultatSolvable using partial update
        ResultatSolvable partialUpdatedResultatSolvable = new ResultatSolvable();
        partialUpdatedResultatSolvable.setId(resultatSolvable.getId());

        partialUpdatedResultatSolvable
            .moisSolvabiliteDate(UPDATED_MOIS_SOLVABILITE_DATE)
            .codeEtatSolvabilite(UPDATED_CODE_ETAT_SOLVABILITE)
            .userCreation(UPDATED_USER_CREATION)
            .majDate(UPDATED_MAJ_DATE);

        restResultatSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultatSolvable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultatSolvable))
            )
            .andExpect(status().isOk());

        // Validate the ResultatSolvable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultatSolvableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedResultatSolvable, resultatSolvable),
            getPersistedResultatSolvable(resultatSolvable)
        );
    }

    @Test
    @Transactional
    void fullUpdateResultatSolvableWithPatch() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the resultatSolvable using partial update
        ResultatSolvable partialUpdatedResultatSolvable = new ResultatSolvable();
        partialUpdatedResultatSolvable.setId(resultatSolvable.getId());

        partialUpdatedResultatSolvable
            .moisSolvabiliteDate(UPDATED_MOIS_SOLVABILITE_DATE)
            .codeEtatSolvabilite(UPDATED_CODE_ETAT_SOLVABILITE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restResultatSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultatSolvable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedResultatSolvable))
            )
            .andExpect(status().isOk());

        // Validate the ResultatSolvable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertResultatSolvableUpdatableFieldsEquals(
            partialUpdatedResultatSolvable,
            getPersistedResultatSolvable(partialUpdatedResultatSolvable)
        );
    }

    @Test
    @Transactional
    void patchNonExistingResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultatSolvableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultatSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(resultatSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultatSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        resultatSolvable.setId(longCount.incrementAndGet());

        // Create the ResultatSolvable
        ResultatSolvableDTO resultatSolvableDTO = resultatSolvableMapper.toDto(resultatSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultatSolvableMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(resultatSolvableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultatSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultatSolvable() throws Exception {
        // Initialize the database
        insertedResultatSolvable = resultatSolvableRepository.saveAndFlush(resultatSolvable);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the resultatSolvable
        restResultatSolvableMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultatSolvable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return resultatSolvableRepository.count();
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

    protected ResultatSolvable getPersistedResultatSolvable(ResultatSolvable resultatSolvable) {
        return resultatSolvableRepository.findById(resultatSolvable.getId()).orElseThrow();
    }

    protected void assertPersistedResultatSolvableToMatchAllProperties(ResultatSolvable expectedResultatSolvable) {
        assertResultatSolvableAllPropertiesEquals(expectedResultatSolvable, getPersistedResultatSolvable(expectedResultatSolvable));
    }

    protected void assertPersistedResultatSolvableToMatchUpdatableProperties(ResultatSolvable expectedResultatSolvable) {
        assertResultatSolvableAllUpdatablePropertiesEquals(
            expectedResultatSolvable,
            getPersistedResultatSolvable(expectedResultatSolvable)
        );
    }
}

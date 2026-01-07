package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.RechercheSolvableAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RechercheSolvable;
import com.mycompany.myapp.repository.RechercheSolvableRepository;
import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
import com.mycompany.myapp.service.mapper.RechercheSolvableMapper;
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
 * Integration tests for the {@link RechercheSolvableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RechercheSolvableResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalTime DEFAULT_RECHERCHE_SOLVABILITE_DEBUT_DATE = LocalTime.NOON;
    private static final LocalTime UPDATED_RECHERCHE_SOLVABILITE_DEBUT_DATE = LocalTime.MAX.withNano(0);

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

    private static final String ENTITY_API_URL = "/api/recherche-solvables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RechercheSolvableRepository rechercheSolvableRepository;

    @Autowired
    private RechercheSolvableMapper rechercheSolvableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRechercheSolvableMockMvc;

    private RechercheSolvable rechercheSolvable;

    private RechercheSolvable insertedRechercheSolvable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RechercheSolvable createEntity() {
        return new RechercheSolvable()
            .rechercheSolvabiliteDebutDate(DEFAULT_RECHERCHE_SOLVABILITE_DEBUT_DATE)
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
    public static RechercheSolvable createUpdatedEntity() {
        return new RechercheSolvable()
            .rechercheSolvabiliteDebutDate(UPDATED_RECHERCHE_SOLVABILITE_DEBUT_DATE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        rechercheSolvable = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRechercheSolvable != null) {
            rechercheSolvableRepository.delete(insertedRechercheSolvable);
            insertedRechercheSolvable = null;
        }
    }

    @Test
    @Transactional
    void createRechercheSolvable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);
        var returnedRechercheSolvableDTO = om.readValue(
            restRechercheSolvableMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechercheSolvableDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RechercheSolvableDTO.class
        );

        // Validate the RechercheSolvable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRechercheSolvable = rechercheSolvableMapper.toEntity(returnedRechercheSolvableDTO);
        assertRechercheSolvableUpdatableFieldsEquals(returnedRechercheSolvable, getPersistedRechercheSolvable(returnedRechercheSolvable));

        insertedRechercheSolvable = returnedRechercheSolvable;
    }

    @Test
    @Transactional
    void createRechercheSolvableWithExistingId() throws Exception {
        // Create the RechercheSolvable with an existing ID
        rechercheSolvable.setId(1L);
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechercheSolvableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechercheSolvableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRechercheSolvables() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        // Get all the rechercheSolvableList
        restRechercheSolvableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rechercheSolvable.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].rechercheSolvabiliteDebutDate").value(
                    hasItem(DEFAULT_RECHERCHE_SOLVABILITE_DEBUT_DATE.format(LOCAL_DATE_TIME_FORMAT))
                )
            )
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getRechercheSolvable() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        // Get the rechercheSolvable
        restRechercheSolvableMockMvc
            .perform(get(ENTITY_API_URL_ID, rechercheSolvable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rechercheSolvable.getId().intValue()))
            .andExpect(
                jsonPath("$.rechercheSolvabiliteDebutDate").value(DEFAULT_RECHERCHE_SOLVABILITE_DEBUT_DATE.format(LOCAL_DATE_TIME_FORMAT))
            )
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingRechercheSolvable() throws Exception {
        // Get the rechercheSolvable
        restRechercheSolvableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRechercheSolvable() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechercheSolvable
        RechercheSolvable updatedRechercheSolvable = rechercheSolvableRepository.findById(rechercheSolvable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRechercheSolvable are not directly saved in db
        em.detach(updatedRechercheSolvable);
        updatedRechercheSolvable
            .rechercheSolvabiliteDebutDate(UPDATED_RECHERCHE_SOLVABILITE_DEBUT_DATE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(updatedRechercheSolvable);

        restRechercheSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rechercheSolvableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rechercheSolvableDTO))
            )
            .andExpect(status().isOk());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRechercheSolvableToMatchAllProperties(updatedRechercheSolvable);
    }

    @Test
    @Transactional
    void putNonExistingRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rechercheSolvableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rechercheSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rechercheSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechercheSolvableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRechercheSolvableWithPatch() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechercheSolvable using partial update
        RechercheSolvable partialUpdatedRechercheSolvable = new RechercheSolvable();
        partialUpdatedRechercheSolvable.setId(rechercheSolvable.getId());

        partialUpdatedRechercheSolvable.userCreation(UPDATED_USER_CREATION);

        restRechercheSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechercheSolvable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRechercheSolvable))
            )
            .andExpect(status().isOk());

        // Validate the RechercheSolvable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRechercheSolvableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRechercheSolvable, rechercheSolvable),
            getPersistedRechercheSolvable(rechercheSolvable)
        );
    }

    @Test
    @Transactional
    void fullUpdateRechercheSolvableWithPatch() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechercheSolvable using partial update
        RechercheSolvable partialUpdatedRechercheSolvable = new RechercheSolvable();
        partialUpdatedRechercheSolvable.setId(rechercheSolvable.getId());

        partialUpdatedRechercheSolvable
            .rechercheSolvabiliteDebutDate(UPDATED_RECHERCHE_SOLVABILITE_DEBUT_DATE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restRechercheSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechercheSolvable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRechercheSolvable))
            )
            .andExpect(status().isOk());

        // Validate the RechercheSolvable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRechercheSolvableUpdatableFieldsEquals(
            partialUpdatedRechercheSolvable,
            getPersistedRechercheSolvable(partialUpdatedRechercheSolvable)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rechercheSolvableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rechercheSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rechercheSolvableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRechercheSolvable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechercheSolvable.setId(longCount.incrementAndGet());

        // Create the RechercheSolvable
        RechercheSolvableDTO rechercheSolvableDTO = rechercheSolvableMapper.toDto(rechercheSolvable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechercheSolvableMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rechercheSolvableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RechercheSolvable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRechercheSolvable() throws Exception {
        // Initialize the database
        insertedRechercheSolvable = rechercheSolvableRepository.saveAndFlush(rechercheSolvable);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rechercheSolvable
        restRechercheSolvableMockMvc
            .perform(delete(ENTITY_API_URL_ID, rechercheSolvable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rechercheSolvableRepository.count();
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

    protected RechercheSolvable getPersistedRechercheSolvable(RechercheSolvable rechercheSolvable) {
        return rechercheSolvableRepository.findById(rechercheSolvable.getId()).orElseThrow();
    }

    protected void assertPersistedRechercheSolvableToMatchAllProperties(RechercheSolvable expectedRechercheSolvable) {
        assertRechercheSolvableAllPropertiesEquals(expectedRechercheSolvable, getPersistedRechercheSolvable(expectedRechercheSolvable));
    }

    protected void assertPersistedRechercheSolvableToMatchUpdatableProperties(RechercheSolvable expectedRechercheSolvable) {
        assertRechercheSolvableAllUpdatablePropertiesEquals(
            expectedRechercheSolvable,
            getPersistedRechercheSolvable(expectedRechercheSolvable)
        );
    }
}

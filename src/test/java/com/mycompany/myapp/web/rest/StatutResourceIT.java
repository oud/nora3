package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.StatutAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Statut;
import com.mycompany.myapp.repository.StatutRepository;
import com.mycompany.myapp.service.dto.StatutDTO;
import com.mycompany.myapp.service.mapper.StatutMapper;
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
 * Integration tests for the {@link StatutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatutResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalTime DEFAULT_STATUT_DEBUT_DATE = LocalTime.NOON;
    private static final LocalTime UPDATED_STATUT_DEBUT_DATE = LocalTime.MAX.withNano(0);

    private static final LocalTime DEFAULT_STATUT_FIN_DATE = LocalTime.NOON;
    private static final LocalTime UPDATED_STATUT_FIN_DATE = LocalTime.MAX.withNano(0);

    private static final String DEFAULT_CODE_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_STATUT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/statuts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutMapper statutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatutMockMvc;

    private Statut statut;

    private Statut insertedStatut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statut createEntity() {
        return new Statut()
            .statutDebutDate(DEFAULT_STATUT_DEBUT_DATE)
            .statutFinDate(DEFAULT_STATUT_FIN_DATE)
            .codeStatut(DEFAULT_CODE_STATUT)
            .motifStatut(DEFAULT_MOTIF_STATUT)
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
    public static Statut createUpdatedEntity() {
        return new Statut()
            .statutDebutDate(UPDATED_STATUT_DEBUT_DATE)
            .statutFinDate(UPDATED_STATUT_FIN_DATE)
            .codeStatut(UPDATED_CODE_STATUT)
            .motifStatut(UPDATED_MOTIF_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        statut = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStatut != null) {
            statutRepository.delete(insertedStatut);
            insertedStatut = null;
        }
    }

    @Test
    @Transactional
    void createStatut() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);
        var returnedStatutDTO = om.readValue(
            restStatutMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statutDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StatutDTO.class
        );

        // Validate the Statut in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStatut = statutMapper.toEntity(returnedStatutDTO);
        assertStatutUpdatableFieldsEquals(returnedStatut, getPersistedStatut(returnedStatut));

        insertedStatut = returnedStatut;
    }

    @Test
    @Transactional
    void createStatutWithExistingId() throws Exception {
        // Create the Statut with an existing ID
        statut.setId(1L);
        StatutDTO statutDTO = statutMapper.toDto(statut);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatuts() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        // Get all the statutList
        restStatutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statut.getId().intValue())))
            .andExpect(jsonPath("$.[*].statutDebutDate").value(hasItem(DEFAULT_STATUT_DEBUT_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].statutFinDate").value(hasItem(DEFAULT_STATUT_FIN_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].codeStatut").value(hasItem(DEFAULT_CODE_STATUT)))
            .andExpect(jsonPath("$.[*].motifStatut").value(hasItem(DEFAULT_MOTIF_STATUT)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getStatut() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        // Get the statut
        restStatutMockMvc
            .perform(get(ENTITY_API_URL_ID, statut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statut.getId().intValue()))
            .andExpect(jsonPath("$.statutDebutDate").value(DEFAULT_STATUT_DEBUT_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.statutFinDate").value(DEFAULT_STATUT_FIN_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.codeStatut").value(DEFAULT_CODE_STATUT))
            .andExpect(jsonPath("$.motifStatut").value(DEFAULT_MOTIF_STATUT))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingStatut() throws Exception {
        // Get the statut
        restStatutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatut() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statut
        Statut updatedStatut = statutRepository.findById(statut.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatut are not directly saved in db
        em.detach(updatedStatut);
        updatedStatut
            .statutDebutDate(UPDATED_STATUT_DEBUT_DATE)
            .statutFinDate(UPDATED_STATUT_FIN_DATE)
            .codeStatut(UPDATED_CODE_STATUT)
            .motifStatut(UPDATED_MOTIF_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        StatutDTO statutDTO = statutMapper.toDto(updatedStatut);

        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statutDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statutDTO))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatutToMatchAllProperties(updatedStatut);
    }

    @Test
    @Transactional
    void putNonExistingStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statutDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatutWithPatch() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statut using partial update
        Statut partialUpdatedStatut = new Statut();
        partialUpdatedStatut.setId(statut.getId());

        partialUpdatedStatut.statutDebutDate(UPDATED_STATUT_DEBUT_DATE).creationDate(UPDATED_CREATION_DATE).userMaj(UPDATED_USER_MAJ);

        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatut))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatutUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStatut, statut), getPersistedStatut(statut));
    }

    @Test
    @Transactional
    void fullUpdateStatutWithPatch() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statut using partial update
        Statut partialUpdatedStatut = new Statut();
        partialUpdatedStatut.setId(statut.getId());

        partialUpdatedStatut
            .statutDebutDate(UPDATED_STATUT_DEBUT_DATE)
            .statutFinDate(UPDATED_STATUT_FIN_DATE)
            .codeStatut(UPDATED_CODE_STATUT)
            .motifStatut(UPDATED_MOTIF_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatut))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatutUpdatableFieldsEquals(partialUpdatedStatut, getPersistedStatut(partialUpdatedStatut));
    }

    @Test
    @Transactional
    void patchNonExistingStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statut.setId(longCount.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(statutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatut() throws Exception {
        // Initialize the database
        insertedStatut = statutRepository.saveAndFlush(statut);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statut
        restStatutMockMvc
            .perform(delete(ENTITY_API_URL_ID, statut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statutRepository.count();
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

    protected Statut getPersistedStatut(Statut statut) {
        return statutRepository.findById(statut.getId()).orElseThrow();
    }

    protected void assertPersistedStatutToMatchAllProperties(Statut expectedStatut) {
        assertStatutAllPropertiesEquals(expectedStatut, getPersistedStatut(expectedStatut));
    }

    protected void assertPersistedStatutToMatchUpdatableProperties(Statut expectedStatut) {
        assertStatutAllUpdatablePropertiesEquals(expectedStatut, getPersistedStatut(expectedStatut));
    }
}

package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.SituationAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Situation;
import com.mycompany.myapp.repository.SituationRepository;
import com.mycompany.myapp.service.dto.SituationDTO;
import com.mycompany.myapp.service.mapper.SituationMapper;
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
 * Integration tests for the {@link SituationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SituationResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalDate DEFAULT_SITUATION_PRO_DEBUT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SITUATION_PRO_DEBUT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SITUATION_PROFIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SITUATION_PROFIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_SITUATION = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SITUATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/situations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private SituationMapper situationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituationMockMvc;

    private Situation situation;

    private Situation insertedSituation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createEntity() {
        return new Situation()
            .situationProDebutDate(DEFAULT_SITUATION_PRO_DEBUT_DATE)
            .situationProfinDate(DEFAULT_SITUATION_PROFIN_DATE)
            .codeSituation(DEFAULT_CODE_SITUATION)
            .commentaire(DEFAULT_COMMENTAIRE)
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
    public static Situation createUpdatedEntity() {
        return new Situation()
            .situationProDebutDate(UPDATED_SITUATION_PRO_DEBUT_DATE)
            .situationProfinDate(UPDATED_SITUATION_PROFIN_DATE)
            .codeSituation(UPDATED_CODE_SITUATION)
            .commentaire(UPDATED_COMMENTAIRE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        situation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSituation != null) {
            situationRepository.delete(insertedSituation);
            insertedSituation = null;
        }
    }

    @Test
    @Transactional
    void createSituation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);
        var returnedSituationDTO = om.readValue(
            restSituationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(situationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SituationDTO.class
        );

        // Validate the Situation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSituation = situationMapper.toEntity(returnedSituationDTO);
        assertSituationUpdatableFieldsEquals(returnedSituation, getPersistedSituation(returnedSituation));

        insertedSituation = returnedSituation;
    }

    @Test
    @Transactional
    void createSituationWithExistingId() throws Exception {
        // Create the Situation with an existing ID
        situation.setId(1L);
        SituationDTO situationDTO = situationMapper.toDto(situation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(situationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSituations() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        // Get all the situationList
        restSituationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situation.getId().intValue())))
            .andExpect(jsonPath("$.[*].situationProDebutDate").value(hasItem(DEFAULT_SITUATION_PRO_DEBUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].situationProfinDate").value(hasItem(DEFAULT_SITUATION_PROFIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeSituation").value(hasItem(DEFAULT_CODE_SITUATION)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getSituation() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        // Get the situation
        restSituationMockMvc
            .perform(get(ENTITY_API_URL_ID, situation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situation.getId().intValue()))
            .andExpect(jsonPath("$.situationProDebutDate").value(DEFAULT_SITUATION_PRO_DEBUT_DATE.toString()))
            .andExpect(jsonPath("$.situationProfinDate").value(DEFAULT_SITUATION_PROFIN_DATE.toString()))
            .andExpect(jsonPath("$.codeSituation").value(DEFAULT_CODE_SITUATION))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingSituation() throws Exception {
        // Get the situation
        restSituationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSituation() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the situation
        Situation updatedSituation = situationRepository.findById(situation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSituation are not directly saved in db
        em.detach(updatedSituation);
        updatedSituation
            .situationProDebutDate(UPDATED_SITUATION_PRO_DEBUT_DATE)
            .situationProfinDate(UPDATED_SITUATION_PROFIN_DATE)
            .codeSituation(UPDATED_CODE_SITUATION)
            .commentaire(UPDATED_COMMENTAIRE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        SituationDTO situationDTO = situationMapper.toDto(updatedSituation);

        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, situationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(situationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSituationToMatchAllProperties(updatedSituation);
    }

    @Test
    @Transactional
    void putNonExistingSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, situationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(situationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(situationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(situationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSituationWithPatch() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the situation using partial update
        Situation partialUpdatedSituation = new Situation();
        partialUpdatedSituation.setId(situation.getId());

        partialUpdatedSituation
            .situationProfinDate(UPDATED_SITUATION_PROFIN_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSituation))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSituationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSituation, situation),
            getPersistedSituation(situation)
        );
    }

    @Test
    @Transactional
    void fullUpdateSituationWithPatch() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the situation using partial update
        Situation partialUpdatedSituation = new Situation();
        partialUpdatedSituation.setId(situation.getId());

        partialUpdatedSituation
            .situationProDebutDate(UPDATED_SITUATION_PRO_DEBUT_DATE)
            .situationProfinDate(UPDATED_SITUATION_PROFIN_DATE)
            .codeSituation(UPDATED_CODE_SITUATION)
            .commentaire(UPDATED_COMMENTAIRE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSituation))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSituationUpdatableFieldsEquals(partialUpdatedSituation, getPersistedSituation(partialUpdatedSituation));
    }

    @Test
    @Transactional
    void patchNonExistingSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, situationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(situationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(situationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSituation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        situation.setId(longCount.incrementAndGet());

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(situationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Situation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSituation() throws Exception {
        // Initialize the database
        insertedSituation = situationRepository.saveAndFlush(situation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the situation
        restSituationMockMvc
            .perform(delete(ENTITY_API_URL_ID, situation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return situationRepository.count();
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

    protected Situation getPersistedSituation(Situation situation) {
        return situationRepository.findById(situation.getId()).orElseThrow();
    }

    protected void assertPersistedSituationToMatchAllProperties(Situation expectedSituation) {
        assertSituationAllPropertiesEquals(expectedSituation, getPersistedSituation(expectedSituation));
    }

    protected void assertPersistedSituationToMatchUpdatableProperties(Situation expectedSituation) {
        assertSituationAllUpdatablePropertiesEquals(expectedSituation, getPersistedSituation(expectedSituation));
    }
}

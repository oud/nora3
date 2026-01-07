package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DemarcheAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Demarche;
import com.mycompany.myapp.repository.DemarcheRepository;
import com.mycompany.myapp.service.dto.DemarcheDTO;
import com.mycompany.myapp.service.mapper.DemarcheMapper;
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
 * Integration tests for the {@link DemarcheResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemarcheResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalDate DEFAULT_DEMARCHE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEMARCHE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUM_DEMARCHE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DEMARCHE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ORIGINE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_STATUT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/demarches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemarcheRepository demarcheRepository;

    @Autowired
    private DemarcheMapper demarcheMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemarcheMockMvc;

    private Demarche demarche;

    private Demarche insertedDemarche;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demarche createEntity() {
        return new Demarche()
            .demarcheDate(DEFAULT_DEMARCHE_DATE)
            .numDemarche(DEFAULT_NUM_DEMARCHE)
            .codeOrigine(DEFAULT_CODE_ORIGINE)
            .codeStatut(DEFAULT_CODE_STATUT)
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
    public static Demarche createUpdatedEntity() {
        return new Demarche()
            .demarcheDate(UPDATED_DEMARCHE_DATE)
            .numDemarche(UPDATED_NUM_DEMARCHE)
            .codeOrigine(UPDATED_CODE_ORIGINE)
            .codeStatut(UPDATED_CODE_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        demarche = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDemarche != null) {
            demarcheRepository.delete(insertedDemarche);
            insertedDemarche = null;
        }
    }

    @Test
    @Transactional
    void createDemarche() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);
        var returnedDemarcheDTO = om.readValue(
            restDemarcheMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demarcheDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DemarcheDTO.class
        );

        // Validate the Demarche in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDemarche = demarcheMapper.toEntity(returnedDemarcheDTO);
        assertDemarcheUpdatableFieldsEquals(returnedDemarche, getPersistedDemarche(returnedDemarche));

        insertedDemarche = returnedDemarche;
    }

    @Test
    @Transactional
    void createDemarcheWithExistingId() throws Exception {
        // Create the Demarche with an existing ID
        demarche.setId(1L);
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemarcheMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demarcheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemarches() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        // Get all the demarcheList
        restDemarcheMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demarche.getId().intValue())))
            .andExpect(jsonPath("$.[*].demarcheDate").value(hasItem(DEFAULT_DEMARCHE_DATE.toString())))
            .andExpect(jsonPath("$.[*].numDemarche").value(hasItem(DEFAULT_NUM_DEMARCHE)))
            .andExpect(jsonPath("$.[*].codeOrigine").value(hasItem(DEFAULT_CODE_ORIGINE)))
            .andExpect(jsonPath("$.[*].codeStatut").value(hasItem(DEFAULT_CODE_STATUT)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getDemarche() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        // Get the demarche
        restDemarcheMockMvc
            .perform(get(ENTITY_API_URL_ID, demarche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demarche.getId().intValue()))
            .andExpect(jsonPath("$.demarcheDate").value(DEFAULT_DEMARCHE_DATE.toString()))
            .andExpect(jsonPath("$.numDemarche").value(DEFAULT_NUM_DEMARCHE))
            .andExpect(jsonPath("$.codeOrigine").value(DEFAULT_CODE_ORIGINE))
            .andExpect(jsonPath("$.codeStatut").value(DEFAULT_CODE_STATUT))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingDemarche() throws Exception {
        // Get the demarche
        restDemarcheMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemarche() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demarche
        Demarche updatedDemarche = demarcheRepository.findById(demarche.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemarche are not directly saved in db
        em.detach(updatedDemarche);
        updatedDemarche
            .demarcheDate(UPDATED_DEMARCHE_DATE)
            .numDemarche(UPDATED_NUM_DEMARCHE)
            .codeOrigine(UPDATED_CODE_ORIGINE)
            .codeStatut(UPDATED_CODE_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(updatedDemarche);

        restDemarcheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demarcheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demarcheDTO))
            )
            .andExpect(status().isOk());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemarcheToMatchAllProperties(updatedDemarche);
    }

    @Test
    @Transactional
    void putNonExistingDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demarcheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demarcheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demarcheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demarcheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemarcheWithPatch() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demarche using partial update
        Demarche partialUpdatedDemarche = new Demarche();
        partialUpdatedDemarche.setId(demarche.getId());

        partialUpdatedDemarche
            .demarcheDate(UPDATED_DEMARCHE_DATE)
            .numDemarche(UPDATED_NUM_DEMARCHE)
            .codeStatut(UPDATED_CODE_STATUT)
            .userCreation(UPDATED_USER_CREATION);

        restDemarcheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemarche.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemarche))
            )
            .andExpect(status().isOk());

        // Validate the Demarche in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemarcheUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDemarche, demarche), getPersistedDemarche(demarche));
    }

    @Test
    @Transactional
    void fullUpdateDemarcheWithPatch() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demarche using partial update
        Demarche partialUpdatedDemarche = new Demarche();
        partialUpdatedDemarche.setId(demarche.getId());

        partialUpdatedDemarche
            .demarcheDate(UPDATED_DEMARCHE_DATE)
            .numDemarche(UPDATED_NUM_DEMARCHE)
            .codeOrigine(UPDATED_CODE_ORIGINE)
            .codeStatut(UPDATED_CODE_STATUT)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restDemarcheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemarche.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemarche))
            )
            .andExpect(status().isOk());

        // Validate the Demarche in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemarcheUpdatableFieldsEquals(partialUpdatedDemarche, getPersistedDemarche(partialUpdatedDemarche));
    }

    @Test
    @Transactional
    void patchNonExistingDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demarcheDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demarcheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demarcheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemarche() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demarche.setId(longCount.incrementAndGet());

        // Create the Demarche
        DemarcheDTO demarcheDTO = demarcheMapper.toDto(demarche);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemarcheMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demarcheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demarche in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemarche() throws Exception {
        // Initialize the database
        insertedDemarche = demarcheRepository.saveAndFlush(demarche);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demarche
        restDemarcheMockMvc
            .perform(delete(ENTITY_API_URL_ID, demarche.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demarcheRepository.count();
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

    protected Demarche getPersistedDemarche(Demarche demarche) {
        return demarcheRepository.findById(demarche.getId()).orElseThrow();
    }

    protected void assertPersistedDemarcheToMatchAllProperties(Demarche expectedDemarche) {
        assertDemarcheAllPropertiesEquals(expectedDemarche, getPersistedDemarche(expectedDemarche));
    }

    protected void assertPersistedDemarcheToMatchUpdatableProperties(Demarche expectedDemarche) {
        assertDemarcheAllUpdatablePropertiesEquals(expectedDemarche, getPersistedDemarche(expectedDemarche));
    }
}

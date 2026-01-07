package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DefaillanceAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Defaillance;
import com.mycompany.myapp.repository.DefaillanceRepository;
import com.mycompany.myapp.service.dto.DefaillanceDTO;
import com.mycompany.myapp.service.mapper.DefaillanceMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DefaillanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DefaillanceResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalDate DEFAULT_MOIS_DEFAILLANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MOIS_DEFAILLANCE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_MONTANT_PA_DUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PA_DUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PA_VERSEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PA_VERSEE = new BigDecimal(2);

    private static final Boolean DEFAULT_FLAG_DETTE_INITIALE = false;
    private static final Boolean UPDATED_FLAG_DETTE_INITIALE = true;

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

    private static final String ENTITY_API_URL = "/api/defaillances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DefaillanceRepository defaillanceRepository;

    @Autowired
    private DefaillanceMapper defaillanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDefaillanceMockMvc;

    private Defaillance defaillance;

    private Defaillance insertedDefaillance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Defaillance createEntity() {
        return new Defaillance()
            .moisDefaillance(DEFAULT_MOIS_DEFAILLANCE)
            .montantPADue(DEFAULT_MONTANT_PA_DUE)
            .montantPAVersee(DEFAULT_MONTANT_PA_VERSEE)
            .flagDetteInitiale(DEFAULT_FLAG_DETTE_INITIALE)
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
    public static Defaillance createUpdatedEntity() {
        return new Defaillance()
            .moisDefaillance(UPDATED_MOIS_DEFAILLANCE)
            .montantPADue(UPDATED_MONTANT_PA_DUE)
            .montantPAVersee(UPDATED_MONTANT_PA_VERSEE)
            .flagDetteInitiale(UPDATED_FLAG_DETTE_INITIALE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        defaillance = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDefaillance != null) {
            defaillanceRepository.delete(insertedDefaillance);
            insertedDefaillance = null;
        }
    }

    @Test
    @Transactional
    void createDefaillance() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);
        var returnedDefaillanceDTO = om.readValue(
            restDefaillanceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(defaillanceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DefaillanceDTO.class
        );

        // Validate the Defaillance in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDefaillance = defaillanceMapper.toEntity(returnedDefaillanceDTO);
        assertDefaillanceUpdatableFieldsEquals(returnedDefaillance, getPersistedDefaillance(returnedDefaillance));

        insertedDefaillance = returnedDefaillance;
    }

    @Test
    @Transactional
    void createDefaillanceWithExistingId() throws Exception {
        // Create the Defaillance with an existing ID
        defaillance.setId(1L);
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefaillanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(defaillanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDefaillances() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        // Get all the defaillanceList
        restDefaillanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defaillance.getId().intValue())))
            .andExpect(jsonPath("$.[*].moisDefaillance").value(hasItem(DEFAULT_MOIS_DEFAILLANCE.toString())))
            .andExpect(jsonPath("$.[*].montantPADue").value(hasItem(sameNumber(DEFAULT_MONTANT_PA_DUE))))
            .andExpect(jsonPath("$.[*].montantPAVersee").value(hasItem(sameNumber(DEFAULT_MONTANT_PA_VERSEE))))
            .andExpect(jsonPath("$.[*].flagDetteInitiale").value(hasItem(DEFAULT_FLAG_DETTE_INITIALE)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getDefaillance() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        // Get the defaillance
        restDefaillanceMockMvc
            .perform(get(ENTITY_API_URL_ID, defaillance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(defaillance.getId().intValue()))
            .andExpect(jsonPath("$.moisDefaillance").value(DEFAULT_MOIS_DEFAILLANCE.toString()))
            .andExpect(jsonPath("$.montantPADue").value(sameNumber(DEFAULT_MONTANT_PA_DUE)))
            .andExpect(jsonPath("$.montantPAVersee").value(sameNumber(DEFAULT_MONTANT_PA_VERSEE)))
            .andExpect(jsonPath("$.flagDetteInitiale").value(DEFAULT_FLAG_DETTE_INITIALE))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingDefaillance() throws Exception {
        // Get the defaillance
        restDefaillanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDefaillance() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the defaillance
        Defaillance updatedDefaillance = defaillanceRepository.findById(defaillance.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDefaillance are not directly saved in db
        em.detach(updatedDefaillance);
        updatedDefaillance
            .moisDefaillance(UPDATED_MOIS_DEFAILLANCE)
            .montantPADue(UPDATED_MONTANT_PA_DUE)
            .montantPAVersee(UPDATED_MONTANT_PA_VERSEE)
            .flagDetteInitiale(UPDATED_FLAG_DETTE_INITIALE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(updatedDefaillance);

        restDefaillanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, defaillanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(defaillanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDefaillanceToMatchAllProperties(updatedDefaillance);
    }

    @Test
    @Transactional
    void putNonExistingDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, defaillanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(defaillanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(defaillanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(defaillanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDefaillanceWithPatch() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the defaillance using partial update
        Defaillance partialUpdatedDefaillance = new Defaillance();
        partialUpdatedDefaillance.setId(defaillance.getId());

        partialUpdatedDefaillance
            .montantPADue(UPDATED_MONTANT_PA_DUE)
            .userCreation(UPDATED_USER_CREATION)
            .userMaj(UPDATED_USER_MAJ)
            .numMaj(UPDATED_NUM_MAJ);

        restDefaillanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDefaillance.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDefaillance))
            )
            .andExpect(status().isOk());

        // Validate the Defaillance in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDefaillanceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDefaillance, defaillance),
            getPersistedDefaillance(defaillance)
        );
    }

    @Test
    @Transactional
    void fullUpdateDefaillanceWithPatch() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the defaillance using partial update
        Defaillance partialUpdatedDefaillance = new Defaillance();
        partialUpdatedDefaillance.setId(defaillance.getId());

        partialUpdatedDefaillance
            .moisDefaillance(UPDATED_MOIS_DEFAILLANCE)
            .montantPADue(UPDATED_MONTANT_PA_DUE)
            .montantPAVersee(UPDATED_MONTANT_PA_VERSEE)
            .flagDetteInitiale(UPDATED_FLAG_DETTE_INITIALE)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restDefaillanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDefaillance.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDefaillance))
            )
            .andExpect(status().isOk());

        // Validate the Defaillance in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDefaillanceUpdatableFieldsEquals(partialUpdatedDefaillance, getPersistedDefaillance(partialUpdatedDefaillance));
    }

    @Test
    @Transactional
    void patchNonExistingDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, defaillanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(defaillanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(defaillanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDefaillance() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        defaillance.setId(longCount.incrementAndGet());

        // Create the Defaillance
        DefaillanceDTO defaillanceDTO = defaillanceMapper.toDto(defaillance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDefaillanceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(defaillanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Defaillance in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDefaillance() throws Exception {
        // Initialize the database
        insertedDefaillance = defaillanceRepository.saveAndFlush(defaillance);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the defaillance
        restDefaillanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, defaillance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return defaillanceRepository.count();
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

    protected Defaillance getPersistedDefaillance(Defaillance defaillance) {
        return defaillanceRepository.findById(defaillance.getId()).orElseThrow();
    }

    protected void assertPersistedDefaillanceToMatchAllProperties(Defaillance expectedDefaillance) {
        assertDefaillanceAllPropertiesEquals(expectedDefaillance, getPersistedDefaillance(expectedDefaillance));
    }

    protected void assertPersistedDefaillanceToMatchUpdatableProperties(Defaillance expectedDefaillance) {
        assertDefaillanceAllUpdatablePropertiesEquals(expectedDefaillance, getPersistedDefaillance(expectedDefaillance));
    }
}

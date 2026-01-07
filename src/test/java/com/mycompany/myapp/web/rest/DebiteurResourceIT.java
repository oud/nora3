package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DebiteurAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.repository.DebiteurRepository;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.service.mapper.DebiteurMapper;
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
 * Integration tests for the {@link DebiteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DebiteurResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String DEFAULT_NIR = "AAAAAAAAAA";
    private static final String UPDATED_NIR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLE_NIR = 1;
    private static final Integer UPDATED_CLE_NIR = 2;

    private static final Integer DEFAULT_NUM_ALLOC_CRISTAL = 1;
    private static final Integer UPDATED_NUM_ALLOC_CRISTAL = 2;

    private static final String DEFAULT_CODE_ORGANISME_CRISTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ORGANISME_CRISTAL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/debiteurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DebiteurRepository debiteurRepository;

    @Autowired
    private DebiteurMapper debiteurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDebiteurMockMvc;

    private Debiteur debiteur;

    private Debiteur insertedDebiteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debiteur createEntity() {
        return new Debiteur()
            .nir(DEFAULT_NIR)
            .cleNir(DEFAULT_CLE_NIR)
            .numAllocCristal(DEFAULT_NUM_ALLOC_CRISTAL)
            .codeOrganismeCristal(DEFAULT_CODE_ORGANISME_CRISTAL)
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
    public static Debiteur createUpdatedEntity() {
        return new Debiteur()
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
    }

    @BeforeEach
    void initTest() {
        debiteur = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDebiteur != null) {
            debiteurRepository.delete(insertedDebiteur);
            insertedDebiteur = null;
        }
    }

    @Test
    @Transactional
    void createDebiteur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);
        var returnedDebiteurDTO = om.readValue(
            restDebiteurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(debiteurDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DebiteurDTO.class
        );

        // Validate the Debiteur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDebiteur = debiteurMapper.toEntity(returnedDebiteurDTO);
        assertDebiteurUpdatableFieldsEquals(returnedDebiteur, getPersistedDebiteur(returnedDebiteur));

        insertedDebiteur = returnedDebiteur;
    }

    @Test
    @Transactional
    void createDebiteurWithExistingId() throws Exception {
        // Create the Debiteur with an existing ID
        debiteur.setId(1L);
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebiteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(debiteurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDebiteurs() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        // Get all the debiteurList
        restDebiteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debiteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nir").value(hasItem(DEFAULT_NIR)))
            .andExpect(jsonPath("$.[*].cleNir").value(hasItem(DEFAULT_CLE_NIR)))
            .andExpect(jsonPath("$.[*].numAllocCristal").value(hasItem(DEFAULT_NUM_ALLOC_CRISTAL)))
            .andExpect(jsonPath("$.[*].codeOrganismeCristal").value(hasItem(DEFAULT_CODE_ORGANISME_CRISTAL)))
            .andExpect(jsonPath("$.[*].codeAgent").value(hasItem(DEFAULT_CODE_AGENT)))
            .andExpect(jsonPath("$.[*].userCreation").value(hasItem(DEFAULT_USER_CREATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].userMaj").value(hasItem(DEFAULT_USER_MAJ)))
            .andExpect(jsonPath("$.[*].majDate").value(hasItem(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].numMaj").value(hasItem(DEFAULT_NUM_MAJ)));
    }

    @Test
    @Transactional
    void getDebiteur() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        // Get the debiteur
        restDebiteurMockMvc
            .perform(get(ENTITY_API_URL_ID, debiteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(debiteur.getId().intValue()))
            .andExpect(jsonPath("$.nir").value(DEFAULT_NIR))
            .andExpect(jsonPath("$.cleNir").value(DEFAULT_CLE_NIR))
            .andExpect(jsonPath("$.numAllocCristal").value(DEFAULT_NUM_ALLOC_CRISTAL))
            .andExpect(jsonPath("$.codeOrganismeCristal").value(DEFAULT_CODE_ORGANISME_CRISTAL))
            .andExpect(jsonPath("$.codeAgent").value(DEFAULT_CODE_AGENT))
            .andExpect(jsonPath("$.userCreation").value(DEFAULT_USER_CREATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.userMaj").value(DEFAULT_USER_MAJ))
            .andExpect(jsonPath("$.majDate").value(DEFAULT_MAJ_DATE.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.numMaj").value(DEFAULT_NUM_MAJ));
    }

    @Test
    @Transactional
    void getNonExistingDebiteur() throws Exception {
        // Get the debiteur
        restDebiteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDebiteur() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the debiteur
        Debiteur updatedDebiteur = debiteurRepository.findById(debiteur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDebiteur are not directly saved in db
        em.detach(updatedDebiteur);
        updatedDebiteur
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(updatedDebiteur);

        restDebiteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debiteurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(debiteurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDebiteurToMatchAllProperties(updatedDebiteur);
    }

    @Test
    @Transactional
    void putNonExistingDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, debiteurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(debiteurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(debiteurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(debiteurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDebiteurWithPatch() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the debiteur using partial update
        Debiteur partialUpdatedDebiteur = new Debiteur();
        partialUpdatedDebiteur.setId(debiteur.getId());

        partialUpdatedDebiteur.cleNir(UPDATED_CLE_NIR).creationDate(UPDATED_CREATION_DATE).userMaj(UPDATED_USER_MAJ);

        restDebiteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebiteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDebiteur))
            )
            .andExpect(status().isOk());

        // Validate the Debiteur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDebiteurUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDebiteur, debiteur), getPersistedDebiteur(debiteur));
    }

    @Test
    @Transactional
    void fullUpdateDebiteurWithPatch() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the debiteur using partial update
        Debiteur partialUpdatedDebiteur = new Debiteur();
        partialUpdatedDebiteur.setId(debiteur.getId());

        partialUpdatedDebiteur
            .nir(UPDATED_NIR)
            .cleNir(UPDATED_CLE_NIR)
            .numAllocCristal(UPDATED_NUM_ALLOC_CRISTAL)
            .codeOrganismeCristal(UPDATED_CODE_ORGANISME_CRISTAL)
            .codeAgent(UPDATED_CODE_AGENT)
            .userCreation(UPDATED_USER_CREATION)
            .creationDate(UPDATED_CREATION_DATE)
            .userMaj(UPDATED_USER_MAJ)
            .majDate(UPDATED_MAJ_DATE)
            .numMaj(UPDATED_NUM_MAJ);

        restDebiteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDebiteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDebiteur))
            )
            .andExpect(status().isOk());

        // Validate the Debiteur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDebiteurUpdatableFieldsEquals(partialUpdatedDebiteur, getPersistedDebiteur(partialUpdatedDebiteur));
    }

    @Test
    @Transactional
    void patchNonExistingDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, debiteurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(debiteurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(debiteurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDebiteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        debiteur.setId(longCount.incrementAndGet());

        // Create the Debiteur
        DebiteurDTO debiteurDTO = debiteurMapper.toDto(debiteur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDebiteurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(debiteurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Debiteur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDebiteur() throws Exception {
        // Initialize the database
        insertedDebiteur = debiteurRepository.saveAndFlush(debiteur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the debiteur
        restDebiteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, debiteur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return debiteurRepository.count();
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

    protected Debiteur getPersistedDebiteur(Debiteur debiteur) {
        return debiteurRepository.findById(debiteur.getId()).orElseThrow();
    }

    protected void assertPersistedDebiteurToMatchAllProperties(Debiteur expectedDebiteur) {
        assertDebiteurAllPropertiesEquals(expectedDebiteur, getPersistedDebiteur(expectedDebiteur));
    }

    protected void assertPersistedDebiteurToMatchUpdatableProperties(Debiteur expectedDebiteur) {
        assertDebiteurAllUpdatablePropertiesEquals(expectedDebiteur, getPersistedDebiteur(expectedDebiteur));
    }
}

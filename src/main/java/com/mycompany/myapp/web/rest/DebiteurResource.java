package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DebiteurRepository;
import com.mycompany.myapp.service.DebiteurService;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Debiteur}.
 */
@RestController
@RequestMapping("/api/debiteurs")
public class DebiteurResource {

    private static final Logger LOG = LoggerFactory.getLogger(DebiteurResource.class);

    private static final String ENTITY_NAME = "debiteur";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final DebiteurService debiteurService;

    private final DebiteurRepository debiteurRepository;

    public DebiteurResource(DebiteurService debiteurService, DebiteurRepository debiteurRepository) {
        this.debiteurService = debiteurService;
        this.debiteurRepository = debiteurRepository;
    }

    /**
     * {@code POST  /debiteurs} : Create a new debiteur.
     *
     * @param debiteurDTO the debiteurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new debiteurDTO, or with status {@code 400 (Bad Request)} if the debiteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DebiteurDTO> createDebiteur(@RequestBody DebiteurDTO debiteurDTO) throws URISyntaxException {
        LOG.debug("REST request to save Debiteur : {}", debiteurDTO);
        if (debiteurDTO.getId() != null) {
            throw new BadRequestAlertException("A new debiteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        debiteurDTO = debiteurService.save(debiteurDTO);
        return ResponseEntity.created(new URI("/api/debiteurs/" + debiteurDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, debiteurDTO.getId().toString()))
            .body(debiteurDTO);
    }

    /**
     * {@code PUT  /debiteurs/:id} : Updates an existing debiteur.
     *
     * @param id the id of the debiteurDTO to save.
     * @param debiteurDTO the debiteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated debiteurDTO,
     * or with status {@code 400 (Bad Request)} if the debiteurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the debiteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DebiteurDTO> updateDebiteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DebiteurDTO debiteurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Debiteur : {}, {}", id, debiteurDTO);
        if (debiteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, debiteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!debiteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        debiteurDTO = debiteurService.update(debiteurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, debiteurDTO.getId().toString()))
            .body(debiteurDTO);
    }

    /**
     * {@code PATCH  /debiteurs/:id} : Partial updates given fields of an existing debiteur, field will ignore if it is null
     *
     * @param id the id of the debiteurDTO to save.
     * @param debiteurDTO the debiteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated debiteurDTO,
     * or with status {@code 400 (Bad Request)} if the debiteurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the debiteurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the debiteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DebiteurDTO> partialUpdateDebiteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DebiteurDTO debiteurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Debiteur partially : {}, {}", id, debiteurDTO);
        if (debiteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, debiteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!debiteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DebiteurDTO> result = debiteurService.partialUpdate(debiteurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, debiteurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /debiteurs} : get all the debiteurs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of debiteurs in body.
     */
    @GetMapping("")
    public List<DebiteurDTO> getAllDebiteurs(@RequestParam(name = "filter", required = false) String filter) {
        if ("dossier-is-null".equals(filter)) {
            LOG.debug("REST request to get all Debiteurs where dossier is null");
            return debiteurService.findAllWhereDossierIsNull();
        }
        LOG.debug("REST request to get all Debiteurs");
        return debiteurService.findAll();
    }

    /**
     * {@code GET  /debiteurs/:id} : get the "id" debiteur.
     *
     * @param id the id of the debiteurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the debiteurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DebiteurDTO> getDebiteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Debiteur : {}", id);
        Optional<DebiteurDTO> debiteurDTO = debiteurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(debiteurDTO);
    }

    /**
     * {@code DELETE  /debiteurs/:id} : delete the "id" debiteur.
     *
     * @param id the id of the debiteurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebiteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Debiteur : {}", id);
        debiteurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

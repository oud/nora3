package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EnfantRepository;
import com.mycompany.myapp.service.EnfantService;
import com.mycompany.myapp.service.dto.EnfantDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Enfant}.
 */
@RestController
@RequestMapping("/api/enfants")
public class EnfantResource {

    private static final Logger LOG = LoggerFactory.getLogger(EnfantResource.class);

    private static final String ENTITY_NAME = "enfant";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final EnfantService enfantService;

    private final EnfantRepository enfantRepository;

    public EnfantResource(EnfantService enfantService, EnfantRepository enfantRepository) {
        this.enfantService = enfantService;
        this.enfantRepository = enfantRepository;
    }

    /**
     * {@code POST  /enfants} : Create a new enfant.
     *
     * @param enfantDTO the enfantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enfantDTO, or with status {@code 400 (Bad Request)} if the enfant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnfantDTO> createEnfant(@RequestBody EnfantDTO enfantDTO) throws URISyntaxException {
        LOG.debug("REST request to save Enfant : {}", enfantDTO);
        if (enfantDTO.getId() != null) {
            throw new BadRequestAlertException("A new enfant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enfantDTO = enfantService.save(enfantDTO);
        return ResponseEntity.created(new URI("/api/enfants/" + enfantDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enfantDTO.getId().toString()))
            .body(enfantDTO);
    }

    /**
     * {@code PUT  /enfants/:id} : Updates an existing enfant.
     *
     * @param id the id of the enfantDTO to save.
     * @param enfantDTO the enfantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfantDTO,
     * or with status {@code 400 (Bad Request)} if the enfantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enfantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnfantDTO> updateEnfant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnfantDTO enfantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Enfant : {}, {}", id, enfantDTO);
        if (enfantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enfantDTO = enfantService.update(enfantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfantDTO.getId().toString()))
            .body(enfantDTO);
    }

    /**
     * {@code PATCH  /enfants/:id} : Partial updates given fields of an existing enfant, field will ignore if it is null
     *
     * @param id the id of the enfantDTO to save.
     * @param enfantDTO the enfantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfantDTO,
     * or with status {@code 400 (Bad Request)} if the enfantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enfantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enfantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnfantDTO> partialUpdateEnfant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnfantDTO enfantDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Enfant partially : {}, {}", id, enfantDTO);
        if (enfantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnfantDTO> result = enfantService.partialUpdate(enfantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /enfants} : get all the enfants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enfants in body.
     */
    @GetMapping("")
    public List<EnfantDTO> getAllEnfants() {
        LOG.debug("REST request to get all Enfants");
        return enfantService.findAll();
    }

    /**
     * {@code GET  /enfants/:id} : get the "id" enfant.
     *
     * @param id the id of the enfantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enfantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnfantDTO> getEnfant(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Enfant : {}", id);
        Optional<EnfantDTO> enfantDTO = enfantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enfantDTO);
    }

    /**
     * {@code DELETE  /enfants/:id} : delete the "id" enfant.
     *
     * @param id the id of the enfantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnfant(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Enfant : {}", id);
        enfantService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

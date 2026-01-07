package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemarcheRepository;
import com.mycompany.myapp.service.DemarcheService;
import com.mycompany.myapp.service.dto.DemarcheDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Demarche}.
 */
@RestController
@RequestMapping("/api/demarches")
public class DemarcheResource {

    private static final Logger LOG = LoggerFactory.getLogger(DemarcheResource.class);

    private static final String ENTITY_NAME = "demarche";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final DemarcheService demarcheService;

    private final DemarcheRepository demarcheRepository;

    public DemarcheResource(DemarcheService demarcheService, DemarcheRepository demarcheRepository) {
        this.demarcheService = demarcheService;
        this.demarcheRepository = demarcheRepository;
    }

    /**
     * {@code POST  /demarches} : Create a new demarche.
     *
     * @param demarcheDTO the demarcheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demarcheDTO, or with status {@code 400 (Bad Request)} if the demarche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemarcheDTO> createDemarche(@RequestBody DemarcheDTO demarcheDTO) throws URISyntaxException {
        LOG.debug("REST request to save Demarche : {}", demarcheDTO);
        if (demarcheDTO.getId() != null) {
            throw new BadRequestAlertException("A new demarche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demarcheDTO = demarcheService.save(demarcheDTO);
        return ResponseEntity.created(new URI("/api/demarches/" + demarcheDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demarcheDTO.getId().toString()))
            .body(demarcheDTO);
    }

    /**
     * {@code PUT  /demarches/:id} : Updates an existing demarche.
     *
     * @param id the id of the demarcheDTO to save.
     * @param demarcheDTO the demarcheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demarcheDTO,
     * or with status {@code 400 (Bad Request)} if the demarcheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demarcheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemarcheDTO> updateDemarche(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemarcheDTO demarcheDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Demarche : {}, {}", id, demarcheDTO);
        if (demarcheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demarcheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demarcheRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demarcheDTO = demarcheService.update(demarcheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demarcheDTO.getId().toString()))
            .body(demarcheDTO);
    }

    /**
     * {@code PATCH  /demarches/:id} : Partial updates given fields of an existing demarche, field will ignore if it is null
     *
     * @param id the id of the demarcheDTO to save.
     * @param demarcheDTO the demarcheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demarcheDTO,
     * or with status {@code 400 (Bad Request)} if the demarcheDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demarcheDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demarcheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemarcheDTO> partialUpdateDemarche(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemarcheDTO demarcheDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Demarche partially : {}, {}", id, demarcheDTO);
        if (demarcheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demarcheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demarcheRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemarcheDTO> result = demarcheService.partialUpdate(demarcheDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demarcheDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demarches} : get all the demarches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demarches in body.
     */
    @GetMapping("")
    public List<DemarcheDTO> getAllDemarches() {
        LOG.debug("REST request to get all Demarches");
        return demarcheService.findAll();
    }

    /**
     * {@code GET  /demarches/:id} : get the "id" demarche.
     *
     * @param id the id of the demarcheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demarcheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemarcheDTO> getDemarche(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Demarche : {}", id);
        Optional<DemarcheDTO> demarcheDTO = demarcheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demarcheDTO);
    }

    /**
     * {@code DELETE  /demarches/:id} : delete the "id" demarche.
     *
     * @param id the id of the demarcheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemarche(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Demarche : {}", id);
        demarcheService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CreancierRepository;
import com.mycompany.myapp.service.CreancierService;
import com.mycompany.myapp.service.dto.CreancierDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Creancier}.
 */
@RestController
@RequestMapping("/api/creanciers")
public class CreancierResource {

    private static final Logger LOG = LoggerFactory.getLogger(CreancierResource.class);

    private static final String ENTITY_NAME = "creancier";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final CreancierService creancierService;

    private final CreancierRepository creancierRepository;

    public CreancierResource(CreancierService creancierService, CreancierRepository creancierRepository) {
        this.creancierService = creancierService;
        this.creancierRepository = creancierRepository;
    }

    /**
     * {@code POST  /creanciers} : Create a new creancier.
     *
     * @param creancierDTO the creancierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creancierDTO, or with status {@code 400 (Bad Request)} if the creancier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CreancierDTO> createCreancier(@RequestBody CreancierDTO creancierDTO) throws URISyntaxException {
        LOG.debug("REST request to save Creancier : {}", creancierDTO);
        if (creancierDTO.getId() != null) {
            throw new BadRequestAlertException("A new creancier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        creancierDTO = creancierService.save(creancierDTO);
        return ResponseEntity.created(new URI("/api/creanciers/" + creancierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, creancierDTO.getId().toString()))
            .body(creancierDTO);
    }

    /**
     * {@code PUT  /creanciers/:id} : Updates an existing creancier.
     *
     * @param id the id of the creancierDTO to save.
     * @param creancierDTO the creancierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creancierDTO,
     * or with status {@code 400 (Bad Request)} if the creancierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creancierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CreancierDTO> updateCreancier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreancierDTO creancierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Creancier : {}, {}", id, creancierDTO);
        if (creancierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creancierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        creancierDTO = creancierService.update(creancierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creancierDTO.getId().toString()))
            .body(creancierDTO);
    }

    /**
     * {@code PATCH  /creanciers/:id} : Partial updates given fields of an existing creancier, field will ignore if it is null
     *
     * @param id the id of the creancierDTO to save.
     * @param creancierDTO the creancierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creancierDTO,
     * or with status {@code 400 (Bad Request)} if the creancierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creancierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creancierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreancierDTO> partialUpdateCreancier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreancierDTO creancierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Creancier partially : {}, {}", id, creancierDTO);
        if (creancierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creancierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreancierDTO> result = creancierService.partialUpdate(creancierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creancierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /creanciers} : get all the creanciers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creanciers in body.
     */
    @GetMapping("")
    public List<CreancierDTO> getAllCreanciers(@RequestParam(name = "filter", required = false) String filter) {
        if ("dossier-is-null".equals(filter)) {
            LOG.debug("REST request to get all Creanciers where dossier is null");
            return creancierService.findAllWhereDossierIsNull();
        }
        LOG.debug("REST request to get all Creanciers");
        return creancierService.findAll();
    }

    /**
     * {@code GET  /creanciers/:id} : get the "id" creancier.
     *
     * @param id the id of the creancierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creancierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreancierDTO> getCreancier(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Creancier : {}", id);
        Optional<CreancierDTO> creancierDTO = creancierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creancierDTO);
    }

    /**
     * {@code DELETE  /creanciers/:id} : delete the "id" creancier.
     *
     * @param id the id of the creancierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreancier(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Creancier : {}", id);
        creancierService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

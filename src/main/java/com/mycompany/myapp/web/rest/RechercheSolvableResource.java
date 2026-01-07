package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RechercheSolvableRepository;
import com.mycompany.myapp.service.RechercheSolvableService;
import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RechercheSolvable}.
 */
@RestController
@RequestMapping("/api/recherche-solvables")
public class RechercheSolvableResource {

    private static final Logger LOG = LoggerFactory.getLogger(RechercheSolvableResource.class);

    private static final String ENTITY_NAME = "rechercheSolvable";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final RechercheSolvableService rechercheSolvableService;

    private final RechercheSolvableRepository rechercheSolvableRepository;

    public RechercheSolvableResource(
        RechercheSolvableService rechercheSolvableService,
        RechercheSolvableRepository rechercheSolvableRepository
    ) {
        this.rechercheSolvableService = rechercheSolvableService;
        this.rechercheSolvableRepository = rechercheSolvableRepository;
    }

    /**
     * {@code POST  /recherche-solvables} : Create a new rechercheSolvable.
     *
     * @param rechercheSolvableDTO the rechercheSolvableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rechercheSolvableDTO, or with status {@code 400 (Bad Request)} if the rechercheSolvable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RechercheSolvableDTO> createRechercheSolvable(@RequestBody RechercheSolvableDTO rechercheSolvableDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save RechercheSolvable : {}", rechercheSolvableDTO);
        if (rechercheSolvableDTO.getId() != null) {
            throw new BadRequestAlertException("A new rechercheSolvable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rechercheSolvableDTO = rechercheSolvableService.save(rechercheSolvableDTO);
        return ResponseEntity.created(new URI("/api/recherche-solvables/" + rechercheSolvableDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rechercheSolvableDTO.getId().toString()))
            .body(rechercheSolvableDTO);
    }

    /**
     * {@code PUT  /recherche-solvables/:id} : Updates an existing rechercheSolvable.
     *
     * @param id the id of the rechercheSolvableDTO to save.
     * @param rechercheSolvableDTO the rechercheSolvableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechercheSolvableDTO,
     * or with status {@code 400 (Bad Request)} if the rechercheSolvableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rechercheSolvableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RechercheSolvableDTO> updateRechercheSolvable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RechercheSolvableDTO rechercheSolvableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RechercheSolvable : {}, {}", id, rechercheSolvableDTO);
        if (rechercheSolvableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechercheSolvableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechercheSolvableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rechercheSolvableDTO = rechercheSolvableService.update(rechercheSolvableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rechercheSolvableDTO.getId().toString()))
            .body(rechercheSolvableDTO);
    }

    /**
     * {@code PATCH  /recherche-solvables/:id} : Partial updates given fields of an existing rechercheSolvable, field will ignore if it is null
     *
     * @param id the id of the rechercheSolvableDTO to save.
     * @param rechercheSolvableDTO the rechercheSolvableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechercheSolvableDTO,
     * or with status {@code 400 (Bad Request)} if the rechercheSolvableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rechercheSolvableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rechercheSolvableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RechercheSolvableDTO> partialUpdateRechercheSolvable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RechercheSolvableDTO rechercheSolvableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RechercheSolvable partially : {}, {}", id, rechercheSolvableDTO);
        if (rechercheSolvableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechercheSolvableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechercheSolvableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RechercheSolvableDTO> result = rechercheSolvableService.partialUpdate(rechercheSolvableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rechercheSolvableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recherche-solvables} : get all the rechercheSolvables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rechercheSolvables in body.
     */
    @GetMapping("")
    public List<RechercheSolvableDTO> getAllRechercheSolvables() {
        LOG.debug("REST request to get all RechercheSolvables");
        return rechercheSolvableService.findAll();
    }

    /**
     * {@code GET  /recherche-solvables/:id} : get the "id" rechercheSolvable.
     *
     * @param id the id of the rechercheSolvableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rechercheSolvableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RechercheSolvableDTO> getRechercheSolvable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RechercheSolvable : {}", id);
        Optional<RechercheSolvableDTO> rechercheSolvableDTO = rechercheSolvableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rechercheSolvableDTO);
    }

    /**
     * {@code DELETE  /recherche-solvables/:id} : delete the "id" rechercheSolvable.
     *
     * @param id the id of the rechercheSolvableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRechercheSolvable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RechercheSolvable : {}", id);
        rechercheSolvableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

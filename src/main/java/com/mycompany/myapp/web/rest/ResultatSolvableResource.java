package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ResultatSolvableRepository;
import com.mycompany.myapp.service.ResultatSolvableService;
import com.mycompany.myapp.service.dto.ResultatSolvableDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ResultatSolvable}.
 */
@RestController
@RequestMapping("/api/resultat-solvables")
public class ResultatSolvableResource {

    private static final Logger LOG = LoggerFactory.getLogger(ResultatSolvableResource.class);

    private static final String ENTITY_NAME = "resultatSolvable";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final ResultatSolvableService resultatSolvableService;

    private final ResultatSolvableRepository resultatSolvableRepository;

    public ResultatSolvableResource(
        ResultatSolvableService resultatSolvableService,
        ResultatSolvableRepository resultatSolvableRepository
    ) {
        this.resultatSolvableService = resultatSolvableService;
        this.resultatSolvableRepository = resultatSolvableRepository;
    }

    /**
     * {@code POST  /resultat-solvables} : Create a new resultatSolvable.
     *
     * @param resultatSolvableDTO the resultatSolvableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultatSolvableDTO, or with status {@code 400 (Bad Request)} if the resultatSolvable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ResultatSolvableDTO> createResultatSolvable(@RequestBody ResultatSolvableDTO resultatSolvableDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ResultatSolvable : {}", resultatSolvableDTO);
        if (resultatSolvableDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultatSolvable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        resultatSolvableDTO = resultatSolvableService.save(resultatSolvableDTO);
        return ResponseEntity.created(new URI("/api/resultat-solvables/" + resultatSolvableDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, resultatSolvableDTO.getId().toString()))
            .body(resultatSolvableDTO);
    }

    /**
     * {@code PUT  /resultat-solvables/:id} : Updates an existing resultatSolvable.
     *
     * @param id the id of the resultatSolvableDTO to save.
     * @param resultatSolvableDTO the resultatSolvableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatSolvableDTO,
     * or with status {@code 400 (Bad Request)} if the resultatSolvableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultatSolvableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultatSolvableDTO> updateResultatSolvable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultatSolvableDTO resultatSolvableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ResultatSolvable : {}, {}", id, resultatSolvableDTO);
        if (resultatSolvableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultatSolvableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultatSolvableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        resultatSolvableDTO = resultatSolvableService.update(resultatSolvableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultatSolvableDTO.getId().toString()))
            .body(resultatSolvableDTO);
    }

    /**
     * {@code PATCH  /resultat-solvables/:id} : Partial updates given fields of an existing resultatSolvable, field will ignore if it is null
     *
     * @param id the id of the resultatSolvableDTO to save.
     * @param resultatSolvableDTO the resultatSolvableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatSolvableDTO,
     * or with status {@code 400 (Bad Request)} if the resultatSolvableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resultatSolvableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultatSolvableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResultatSolvableDTO> partialUpdateResultatSolvable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultatSolvableDTO resultatSolvableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ResultatSolvable partially : {}, {}", id, resultatSolvableDTO);
        if (resultatSolvableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultatSolvableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultatSolvableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultatSolvableDTO> result = resultatSolvableService.partialUpdate(resultatSolvableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultatSolvableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /resultat-solvables} : get all the resultatSolvables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultatSolvables in body.
     */
    @GetMapping("")
    public List<ResultatSolvableDTO> getAllResultatSolvables() {
        LOG.debug("REST request to get all ResultatSolvables");
        return resultatSolvableService.findAll();
    }

    /**
     * {@code GET  /resultat-solvables/:id} : get the "id" resultatSolvable.
     *
     * @param id the id of the resultatSolvableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultatSolvableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultatSolvableDTO> getResultatSolvable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ResultatSolvable : {}", id);
        Optional<ResultatSolvableDTO> resultatSolvableDTO = resultatSolvableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultatSolvableDTO);
    }

    /**
     * {@code DELETE  /resultat-solvables/:id} : delete the "id" resultatSolvable.
     *
     * @param id the id of the resultatSolvableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultatSolvable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ResultatSolvable : {}", id);
        resultatSolvableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

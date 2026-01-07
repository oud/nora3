package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SituationRepository;
import com.mycompany.myapp.service.SituationService;
import com.mycompany.myapp.service.dto.SituationDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Situation}.
 */
@RestController
@RequestMapping("/api/situations")
public class SituationResource {

    private static final Logger LOG = LoggerFactory.getLogger(SituationResource.class);

    private static final String ENTITY_NAME = "situation";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final SituationService situationService;

    private final SituationRepository situationRepository;

    public SituationResource(SituationService situationService, SituationRepository situationRepository) {
        this.situationService = situationService;
        this.situationRepository = situationRepository;
    }

    /**
     * {@code POST  /situations} : Create a new situation.
     *
     * @param situationDTO the situationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situationDTO, or with status {@code 400 (Bad Request)} if the situation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SituationDTO> createSituation(@RequestBody SituationDTO situationDTO) throws URISyntaxException {
        LOG.debug("REST request to save Situation : {}", situationDTO);
        if (situationDTO.getId() != null) {
            throw new BadRequestAlertException("A new situation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        situationDTO = situationService.save(situationDTO);
        return ResponseEntity.created(new URI("/api/situations/" + situationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, situationDTO.getId().toString()))
            .body(situationDTO);
    }

    /**
     * {@code PUT  /situations/:id} : Updates an existing situation.
     *
     * @param id the id of the situationDTO to save.
     * @param situationDTO the situationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationDTO,
     * or with status {@code 400 (Bad Request)} if the situationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SituationDTO> updateSituation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituationDTO situationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Situation : {}, {}", id, situationDTO);
        if (situationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        situationDTO = situationService.update(situationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationDTO.getId().toString()))
            .body(situationDTO);
    }

    /**
     * {@code PATCH  /situations/:id} : Partial updates given fields of an existing situation, field will ignore if it is null
     *
     * @param id the id of the situationDTO to save.
     * @param situationDTO the situationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationDTO,
     * or with status {@code 400 (Bad Request)} if the situationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the situationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the situationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SituationDTO> partialUpdateSituation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituationDTO situationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Situation partially : {}, {}", id, situationDTO);
        if (situationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SituationDTO> result = situationService.partialUpdate(situationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /situations} : get all the situations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situations in body.
     */
    @GetMapping("")
    public List<SituationDTO> getAllSituations() {
        LOG.debug("REST request to get all Situations");
        return situationService.findAll();
    }

    /**
     * {@code GET  /situations/:id} : get the "id" situation.
     *
     * @param id the id of the situationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SituationDTO> getSituation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Situation : {}", id);
        Optional<SituationDTO> situationDTO = situationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situationDTO);
    }

    /**
     * {@code DELETE  /situations/:id} : delete the "id" situation.
     *
     * @param id the id of the situationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSituation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Situation : {}", id);
        situationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

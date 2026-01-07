package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DefaillanceRepository;
import com.mycompany.myapp.service.DefaillanceService;
import com.mycompany.myapp.service.dto.DefaillanceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Defaillance}.
 */
@RestController
@RequestMapping("/api/defaillances")
public class DefaillanceResource {

    private static final Logger LOG = LoggerFactory.getLogger(DefaillanceResource.class);

    private static final String ENTITY_NAME = "defaillance";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final DefaillanceService defaillanceService;

    private final DefaillanceRepository defaillanceRepository;

    public DefaillanceResource(DefaillanceService defaillanceService, DefaillanceRepository defaillanceRepository) {
        this.defaillanceService = defaillanceService;
        this.defaillanceRepository = defaillanceRepository;
    }

    /**
     * {@code POST  /defaillances} : Create a new defaillance.
     *
     * @param defaillanceDTO the defaillanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new defaillanceDTO, or with status {@code 400 (Bad Request)} if the defaillance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DefaillanceDTO> createDefaillance(@RequestBody DefaillanceDTO defaillanceDTO) throws URISyntaxException {
        LOG.debug("REST request to save Defaillance : {}", defaillanceDTO);
        if (defaillanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new defaillance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        defaillanceDTO = defaillanceService.save(defaillanceDTO);
        return ResponseEntity.created(new URI("/api/defaillances/" + defaillanceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, defaillanceDTO.getId().toString()))
            .body(defaillanceDTO);
    }

    /**
     * {@code PUT  /defaillances/:id} : Updates an existing defaillance.
     *
     * @param id the id of the defaillanceDTO to save.
     * @param defaillanceDTO the defaillanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaillanceDTO,
     * or with status {@code 400 (Bad Request)} if the defaillanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the defaillanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DefaillanceDTO> updateDefaillance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DefaillanceDTO defaillanceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Defaillance : {}, {}", id, defaillanceDTO);
        if (defaillanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, defaillanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!defaillanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        defaillanceDTO = defaillanceService.update(defaillanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaillanceDTO.getId().toString()))
            .body(defaillanceDTO);
    }

    /**
     * {@code PATCH  /defaillances/:id} : Partial updates given fields of an existing defaillance, field will ignore if it is null
     *
     * @param id the id of the defaillanceDTO to save.
     * @param defaillanceDTO the defaillanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaillanceDTO,
     * or with status {@code 400 (Bad Request)} if the defaillanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the defaillanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the defaillanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DefaillanceDTO> partialUpdateDefaillance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DefaillanceDTO defaillanceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Defaillance partially : {}, {}", id, defaillanceDTO);
        if (defaillanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, defaillanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!defaillanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DefaillanceDTO> result = defaillanceService.partialUpdate(defaillanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaillanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /defaillances} : get all the defaillances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of defaillances in body.
     */
    @GetMapping("")
    public List<DefaillanceDTO> getAllDefaillances() {
        LOG.debug("REST request to get all Defaillances");
        return defaillanceService.findAll();
    }

    /**
     * {@code GET  /defaillances/:id} : get the "id" defaillance.
     *
     * @param id the id of the defaillanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the defaillanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DefaillanceDTO> getDefaillance(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Defaillance : {}", id);
        Optional<DefaillanceDTO> defaillanceDTO = defaillanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(defaillanceDTO);
    }

    /**
     * {@code DELETE  /defaillances/:id} : delete the "id" defaillance.
     *
     * @param id the id of the defaillanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDefaillance(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Defaillance : {}", id);
        defaillanceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

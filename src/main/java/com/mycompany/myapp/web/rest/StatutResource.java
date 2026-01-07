package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.StatutRepository;
import com.mycompany.myapp.service.StatutService;
import com.mycompany.myapp.service.dto.StatutDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Statut}.
 */
@RestController
@RequestMapping("/api/statuts")
public class StatutResource {

    private static final Logger LOG = LoggerFactory.getLogger(StatutResource.class);

    private static final String ENTITY_NAME = "statut";

    @Value("${jhipster.clientApp.name:nora3}")
    private String applicationName;

    private final StatutService statutService;

    private final StatutRepository statutRepository;

    public StatutResource(StatutService statutService, StatutRepository statutRepository) {
        this.statutService = statutService;
        this.statutRepository = statutRepository;
    }

    /**
     * {@code POST  /statuts} : Create a new statut.
     *
     * @param statutDTO the statutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statutDTO, or with status {@code 400 (Bad Request)} if the statut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StatutDTO> createStatut(@RequestBody StatutDTO statutDTO) throws URISyntaxException {
        LOG.debug("REST request to save Statut : {}", statutDTO);
        if (statutDTO.getId() != null) {
            throw new BadRequestAlertException("A new statut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        statutDTO = statutService.save(statutDTO);
        return ResponseEntity.created(new URI("/api/statuts/" + statutDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, statutDTO.getId().toString()))
            .body(statutDTO);
    }

    /**
     * {@code PUT  /statuts/:id} : Updates an existing statut.
     *
     * @param id the id of the statutDTO to save.
     * @param statutDTO the statutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statutDTO,
     * or with status {@code 400 (Bad Request)} if the statutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatutDTO> updateStatut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatutDTO statutDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Statut : {}, {}", id, statutDTO);
        if (statutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        statutDTO = statutService.update(statutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statutDTO.getId().toString()))
            .body(statutDTO);
    }

    /**
     * {@code PATCH  /statuts/:id} : Partial updates given fields of an existing statut, field will ignore if it is null
     *
     * @param id the id of the statutDTO to save.
     * @param statutDTO the statutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statutDTO,
     * or with status {@code 400 (Bad Request)} if the statutDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statutDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatutDTO> partialUpdateStatut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatutDTO statutDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Statut partially : {}, {}", id, statutDTO);
        if (statutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatutDTO> result = statutService.partialUpdate(statutDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statutDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statuts} : get all the statuts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statuts in body.
     */
    @GetMapping("")
    public List<StatutDTO> getAllStatuts() {
        LOG.debug("REST request to get all Statuts");
        return statutService.findAll();
    }

    /**
     * {@code GET  /statuts/:id} : get the "id" statut.
     *
     * @param id the id of the statutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatutDTO> getStatut(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Statut : {}", id);
        Optional<StatutDTO> statutDTO = statutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statutDTO);
    }

    /**
     * {@code DELETE  /statuts/:id} : delete the "id" statut.
     *
     * @param id the id of the statutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatut(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Statut : {}", id);
        statutService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

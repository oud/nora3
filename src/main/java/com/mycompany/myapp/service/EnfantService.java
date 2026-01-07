package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EnfantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Enfant}.
 */
public interface EnfantService {
    /**
     * Save a enfant.
     *
     * @param enfantDTO the entity to save.
     * @return the persisted entity.
     */
    EnfantDTO save(EnfantDTO enfantDTO);

    /**
     * Updates a enfant.
     *
     * @param enfantDTO the entity to update.
     * @return the persisted entity.
     */
    EnfantDTO update(EnfantDTO enfantDTO);

    /**
     * Partially updates a enfant.
     *
     * @param enfantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnfantDTO> partialUpdate(EnfantDTO enfantDTO);

    /**
     * Get all the enfants.
     *
     * @return the list of entities.
     */
    List<EnfantDTO> findAll();

    /**
     * Get the "id" enfant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnfantDTO> findOne(Long id);

    /**
     * Delete the "id" enfant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

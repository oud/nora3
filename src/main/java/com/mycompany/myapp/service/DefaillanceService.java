package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DefaillanceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Defaillance}.
 */
public interface DefaillanceService {
    /**
     * Save a defaillance.
     *
     * @param defaillanceDTO the entity to save.
     * @return the persisted entity.
     */
    DefaillanceDTO save(DefaillanceDTO defaillanceDTO);

    /**
     * Updates a defaillance.
     *
     * @param defaillanceDTO the entity to update.
     * @return the persisted entity.
     */
    DefaillanceDTO update(DefaillanceDTO defaillanceDTO);

    /**
     * Partially updates a defaillance.
     *
     * @param defaillanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DefaillanceDTO> partialUpdate(DefaillanceDTO defaillanceDTO);

    /**
     * Get all the defaillances.
     *
     * @return the list of entities.
     */
    List<DefaillanceDTO> findAll();

    /**
     * Get the "id" defaillance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DefaillanceDTO> findOne(Long id);

    /**
     * Delete the "id" defaillance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RechercheSolvable}.
 */
public interface RechercheSolvableService {
    /**
     * Save a rechercheSolvable.
     *
     * @param rechercheSolvableDTO the entity to save.
     * @return the persisted entity.
     */
    RechercheSolvableDTO save(RechercheSolvableDTO rechercheSolvableDTO);

    /**
     * Updates a rechercheSolvable.
     *
     * @param rechercheSolvableDTO the entity to update.
     * @return the persisted entity.
     */
    RechercheSolvableDTO update(RechercheSolvableDTO rechercheSolvableDTO);

    /**
     * Partially updates a rechercheSolvable.
     *
     * @param rechercheSolvableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RechercheSolvableDTO> partialUpdate(RechercheSolvableDTO rechercheSolvableDTO);

    /**
     * Get all the rechercheSolvables.
     *
     * @return the list of entities.
     */
    List<RechercheSolvableDTO> findAll();

    /**
     * Get the "id" rechercheSolvable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RechercheSolvableDTO> findOne(Long id);

    /**
     * Delete the "id" rechercheSolvable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

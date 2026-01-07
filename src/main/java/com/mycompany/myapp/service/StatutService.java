package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.StatutDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Statut}.
 */
public interface StatutService {
    /**
     * Save a statut.
     *
     * @param statutDTO the entity to save.
     * @return the persisted entity.
     */
    StatutDTO save(StatutDTO statutDTO);

    /**
     * Updates a statut.
     *
     * @param statutDTO the entity to update.
     * @return the persisted entity.
     */
    StatutDTO update(StatutDTO statutDTO);

    /**
     * Partially updates a statut.
     *
     * @param statutDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatutDTO> partialUpdate(StatutDTO statutDTO);

    /**
     * Get all the statuts.
     *
     * @return the list of entities.
     */
    List<StatutDTO> findAll();

    /**
     * Get the "id" statut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatutDTO> findOne(Long id);

    /**
     * Delete the "id" statut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

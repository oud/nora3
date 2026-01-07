package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SituationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Situation}.
 */
public interface SituationService {
    /**
     * Save a situation.
     *
     * @param situationDTO the entity to save.
     * @return the persisted entity.
     */
    SituationDTO save(SituationDTO situationDTO);

    /**
     * Updates a situation.
     *
     * @param situationDTO the entity to update.
     * @return the persisted entity.
     */
    SituationDTO update(SituationDTO situationDTO);

    /**
     * Partially updates a situation.
     *
     * @param situationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SituationDTO> partialUpdate(SituationDTO situationDTO);

    /**
     * Get all the situations.
     *
     * @return the list of entities.
     */
    List<SituationDTO> findAll();

    /**
     * Get the "id" situation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SituationDTO> findOne(Long id);

    /**
     * Delete the "id" situation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

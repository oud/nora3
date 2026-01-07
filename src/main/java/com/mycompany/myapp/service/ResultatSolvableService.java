package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ResultatSolvableDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ResultatSolvable}.
 */
public interface ResultatSolvableService {
    /**
     * Save a resultatSolvable.
     *
     * @param resultatSolvableDTO the entity to save.
     * @return the persisted entity.
     */
    ResultatSolvableDTO save(ResultatSolvableDTO resultatSolvableDTO);

    /**
     * Updates a resultatSolvable.
     *
     * @param resultatSolvableDTO the entity to update.
     * @return the persisted entity.
     */
    ResultatSolvableDTO update(ResultatSolvableDTO resultatSolvableDTO);

    /**
     * Partially updates a resultatSolvable.
     *
     * @param resultatSolvableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResultatSolvableDTO> partialUpdate(ResultatSolvableDTO resultatSolvableDTO);

    /**
     * Get all the resultatSolvables.
     *
     * @return the list of entities.
     */
    List<ResultatSolvableDTO> findAll();

    /**
     * Get the "id" resultatSolvable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultatSolvableDTO> findOne(Long id);

    /**
     * Delete the "id" resultatSolvable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

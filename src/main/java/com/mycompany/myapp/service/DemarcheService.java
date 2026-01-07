package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemarcheDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Demarche}.
 */
public interface DemarcheService {
    /**
     * Save a demarche.
     *
     * @param demarcheDTO the entity to save.
     * @return the persisted entity.
     */
    DemarcheDTO save(DemarcheDTO demarcheDTO);

    /**
     * Updates a demarche.
     *
     * @param demarcheDTO the entity to update.
     * @return the persisted entity.
     */
    DemarcheDTO update(DemarcheDTO demarcheDTO);

    /**
     * Partially updates a demarche.
     *
     * @param demarcheDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemarcheDTO> partialUpdate(DemarcheDTO demarcheDTO);

    /**
     * Get all the demarches.
     *
     * @return the list of entities.
     */
    List<DemarcheDTO> findAll();

    /**
     * Get the "id" demarche.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemarcheDTO> findOne(Long id);

    /**
     * Delete the "id" demarche.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

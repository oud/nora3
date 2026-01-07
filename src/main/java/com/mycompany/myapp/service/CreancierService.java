package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CreancierDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Creancier}.
 */
public interface CreancierService {
    /**
     * Save a creancier.
     *
     * @param creancierDTO the entity to save.
     * @return the persisted entity.
     */
    CreancierDTO save(CreancierDTO creancierDTO);

    /**
     * Updates a creancier.
     *
     * @param creancierDTO the entity to update.
     * @return the persisted entity.
     */
    CreancierDTO update(CreancierDTO creancierDTO);

    /**
     * Partially updates a creancier.
     *
     * @param creancierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreancierDTO> partialUpdate(CreancierDTO creancierDTO);

    /**
     * Get all the creanciers.
     *
     * @return the list of entities.
     */
    List<CreancierDTO> findAll();

    /**
     * Get all the CreancierDTO where Dossier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CreancierDTO> findAllWhereDossierIsNull();

    /**
     * Get the "id" creancier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreancierDTO> findOne(Long id);

    /**
     * Delete the "id" creancier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

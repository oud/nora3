package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DebiteurDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Debiteur}.
 */
public interface DebiteurService {
    /**
     * Save a debiteur.
     *
     * @param debiteurDTO the entity to save.
     * @return the persisted entity.
     */
    DebiteurDTO save(DebiteurDTO debiteurDTO);

    /**
     * Updates a debiteur.
     *
     * @param debiteurDTO the entity to update.
     * @return the persisted entity.
     */
    DebiteurDTO update(DebiteurDTO debiteurDTO);

    /**
     * Partially updates a debiteur.
     *
     * @param debiteurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DebiteurDTO> partialUpdate(DebiteurDTO debiteurDTO);

    /**
     * Get all the debiteurs.
     *
     * @return the list of entities.
     */
    List<DebiteurDTO> findAll();

    /**
     * Get all the DebiteurDTO where Dossier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DebiteurDTO> findAllWhereDossierIsNull();

    /**
     * Get the "id" debiteur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DebiteurDTO> findOne(Long id);

    /**
     * Delete the "id" debiteur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

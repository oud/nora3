package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Debiteur;
import com.mycompany.myapp.repository.DebiteurRepository;
import com.mycompany.myapp.service.DebiteurService;
import com.mycompany.myapp.service.dto.DebiteurDTO;
import com.mycompany.myapp.service.mapper.DebiteurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Debiteur}.
 */
@Service
@Transactional
public class DebiteurServiceImpl implements DebiteurService {

    private static final Logger LOG = LoggerFactory.getLogger(DebiteurServiceImpl.class);

    private final DebiteurRepository debiteurRepository;

    private final DebiteurMapper debiteurMapper;

    public DebiteurServiceImpl(DebiteurRepository debiteurRepository, DebiteurMapper debiteurMapper) {
        this.debiteurRepository = debiteurRepository;
        this.debiteurMapper = debiteurMapper;
    }

    @Override
    public DebiteurDTO save(DebiteurDTO debiteurDTO) {
        LOG.debug("Request to save Debiteur : {}", debiteurDTO);
        Debiteur debiteur = debiteurMapper.toEntity(debiteurDTO);
        debiteur = debiteurRepository.save(debiteur);
        return debiteurMapper.toDto(debiteur);
    }

    @Override
    public DebiteurDTO update(DebiteurDTO debiteurDTO) {
        LOG.debug("Request to update Debiteur : {}", debiteurDTO);
        Debiteur debiteur = debiteurMapper.toEntity(debiteurDTO);
        debiteur = debiteurRepository.save(debiteur);
        return debiteurMapper.toDto(debiteur);
    }

    @Override
    public Optional<DebiteurDTO> partialUpdate(DebiteurDTO debiteurDTO) {
        LOG.debug("Request to partially update Debiteur : {}", debiteurDTO);

        return debiteurRepository
            .findById(debiteurDTO.getId())
            .map(existingDebiteur -> {
                debiteurMapper.partialUpdate(existingDebiteur, debiteurDTO);

                return existingDebiteur;
            })
            .map(debiteurRepository::save)
            .map(debiteurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DebiteurDTO> findAll() {
        LOG.debug("Request to get all Debiteurs");
        return debiteurRepository.findAll().stream().map(debiteurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the debiteurs where Dossier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DebiteurDTO> findAllWhereDossierIsNull() {
        LOG.debug("Request to get all debiteurs where Dossier is null");
        return StreamSupport.stream(debiteurRepository.findAll().spliterator(), false)
            .filter(debiteur -> debiteur.getDossier() == null)
            .map(debiteurMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DebiteurDTO> findOne(Long id) {
        LOG.debug("Request to get Debiteur : {}", id);
        return debiteurRepository.findById(id).map(debiteurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Debiteur : {}", id);
        debiteurRepository.deleteById(id);
    }
}

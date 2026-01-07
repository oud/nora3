package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Creancier;
import com.mycompany.myapp.repository.CreancierRepository;
import com.mycompany.myapp.service.CreancierService;
import com.mycompany.myapp.service.dto.CreancierDTO;
import com.mycompany.myapp.service.mapper.CreancierMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Creancier}.
 */
@Service
@Transactional
public class CreancierServiceImpl implements CreancierService {

    private static final Logger LOG = LoggerFactory.getLogger(CreancierServiceImpl.class);

    private final CreancierRepository creancierRepository;

    private final CreancierMapper creancierMapper;

    public CreancierServiceImpl(CreancierRepository creancierRepository, CreancierMapper creancierMapper) {
        this.creancierRepository = creancierRepository;
        this.creancierMapper = creancierMapper;
    }

    @Override
    public CreancierDTO save(CreancierDTO creancierDTO) {
        LOG.debug("Request to save Creancier : {}", creancierDTO);
        Creancier creancier = creancierMapper.toEntity(creancierDTO);
        creancier = creancierRepository.save(creancier);
        return creancierMapper.toDto(creancier);
    }

    @Override
    public CreancierDTO update(CreancierDTO creancierDTO) {
        LOG.debug("Request to update Creancier : {}", creancierDTO);
        Creancier creancier = creancierMapper.toEntity(creancierDTO);
        creancier = creancierRepository.save(creancier);
        return creancierMapper.toDto(creancier);
    }

    @Override
    public Optional<CreancierDTO> partialUpdate(CreancierDTO creancierDTO) {
        LOG.debug("Request to partially update Creancier : {}", creancierDTO);

        return creancierRepository
            .findById(creancierDTO.getId())
            .map(existingCreancier -> {
                creancierMapper.partialUpdate(existingCreancier, creancierDTO);

                return existingCreancier;
            })
            .map(creancierRepository::save)
            .map(creancierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreancierDTO> findAll() {
        LOG.debug("Request to get all Creanciers");
        return creancierRepository.findAll().stream().map(creancierMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the creanciers where Dossier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreancierDTO> findAllWhereDossierIsNull() {
        LOG.debug("Request to get all creanciers where Dossier is null");
        return StreamSupport.stream(creancierRepository.findAll().spliterator(), false)
            .filter(creancier -> creancier.getDossier() == null)
            .map(creancierMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreancierDTO> findOne(Long id) {
        LOG.debug("Request to get Creancier : {}", id);
        return creancierRepository.findById(id).map(creancierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Creancier : {}", id);
        creancierRepository.deleteById(id);
    }
}

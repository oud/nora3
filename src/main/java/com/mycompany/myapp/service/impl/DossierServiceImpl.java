package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.DossierService;
import com.mycompany.myapp.service.dto.DossierDTO;
import com.mycompany.myapp.service.mapper.DossierMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Dossier}.
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private static final Logger LOG = LoggerFactory.getLogger(DossierServiceImpl.class);

    private final DossierRepository dossierRepository;

    private final DossierMapper dossierMapper;

    public DossierServiceImpl(DossierRepository dossierRepository, DossierMapper dossierMapper) {
        this.dossierRepository = dossierRepository;
        this.dossierMapper = dossierMapper;
    }

    @Override
    public DossierDTO save(DossierDTO dossierDTO) {
        LOG.debug("Request to save Dossier : {}", dossierDTO);
        Dossier dossier = dossierMapper.toEntity(dossierDTO);
        dossier = dossierRepository.save(dossier);
        return dossierMapper.toDto(dossier);
    }

    @Override
    public DossierDTO update(DossierDTO dossierDTO) {
        LOG.debug("Request to update Dossier : {}", dossierDTO);
        Dossier dossier = dossierMapper.toEntity(dossierDTO);
        dossier = dossierRepository.save(dossier);
        return dossierMapper.toDto(dossier);
    }

    @Override
    public Optional<DossierDTO> partialUpdate(DossierDTO dossierDTO) {
        LOG.debug("Request to partially update Dossier : {}", dossierDTO);

        return dossierRepository
            .findById(dossierDTO.getId())
            .map(existingDossier -> {
                dossierMapper.partialUpdate(existingDossier, dossierDTO);

                return existingDossier;
            })
            .map(dossierRepository::save)
            .map(dossierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DossierDTO> findAll() {
        LOG.debug("Request to get all Dossiers");
        return dossierRepository.findAll().stream().map(dossierMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DossierDTO> findOne(Long id) {
        LOG.debug("Request to get Dossier : {}", id);
        return dossierRepository.findById(id).map(dossierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Dossier : {}", id);
        dossierRepository.deleteById(id);
    }
}

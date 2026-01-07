package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Demarche;
import com.mycompany.myapp.repository.DemarcheRepository;
import com.mycompany.myapp.service.DemarcheService;
import com.mycompany.myapp.service.dto.DemarcheDTO;
import com.mycompany.myapp.service.mapper.DemarcheMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Demarche}.
 */
@Service
@Transactional
public class DemarcheServiceImpl implements DemarcheService {

    private static final Logger LOG = LoggerFactory.getLogger(DemarcheServiceImpl.class);

    private final DemarcheRepository demarcheRepository;

    private final DemarcheMapper demarcheMapper;

    public DemarcheServiceImpl(DemarcheRepository demarcheRepository, DemarcheMapper demarcheMapper) {
        this.demarcheRepository = demarcheRepository;
        this.demarcheMapper = demarcheMapper;
    }

    @Override
    public DemarcheDTO save(DemarcheDTO demarcheDTO) {
        LOG.debug("Request to save Demarche : {}", demarcheDTO);
        Demarche demarche = demarcheMapper.toEntity(demarcheDTO);
        demarche = demarcheRepository.save(demarche);
        return demarcheMapper.toDto(demarche);
    }

    @Override
    public DemarcheDTO update(DemarcheDTO demarcheDTO) {
        LOG.debug("Request to update Demarche : {}", demarcheDTO);
        Demarche demarche = demarcheMapper.toEntity(demarcheDTO);
        demarche = demarcheRepository.save(demarche);
        return demarcheMapper.toDto(demarche);
    }

    @Override
    public Optional<DemarcheDTO> partialUpdate(DemarcheDTO demarcheDTO) {
        LOG.debug("Request to partially update Demarche : {}", demarcheDTO);

        return demarcheRepository
            .findById(demarcheDTO.getId())
            .map(existingDemarche -> {
                demarcheMapper.partialUpdate(existingDemarche, demarcheDTO);

                return existingDemarche;
            })
            .map(demarcheRepository::save)
            .map(demarcheMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemarcheDTO> findAll() {
        LOG.debug("Request to get all Demarches");
        return demarcheRepository.findAll().stream().map(demarcheMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemarcheDTO> findOne(Long id) {
        LOG.debug("Request to get Demarche : {}", id);
        return demarcheRepository.findById(id).map(demarcheMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Demarche : {}", id);
        demarcheRepository.deleteById(id);
    }
}

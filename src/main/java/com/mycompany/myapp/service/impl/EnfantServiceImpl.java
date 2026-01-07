package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Enfant;
import com.mycompany.myapp.repository.EnfantRepository;
import com.mycompany.myapp.service.EnfantService;
import com.mycompany.myapp.service.dto.EnfantDTO;
import com.mycompany.myapp.service.mapper.EnfantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Enfant}.
 */
@Service
@Transactional
public class EnfantServiceImpl implements EnfantService {

    private static final Logger LOG = LoggerFactory.getLogger(EnfantServiceImpl.class);

    private final EnfantRepository enfantRepository;

    private final EnfantMapper enfantMapper;

    public EnfantServiceImpl(EnfantRepository enfantRepository, EnfantMapper enfantMapper) {
        this.enfantRepository = enfantRepository;
        this.enfantMapper = enfantMapper;
    }

    @Override
    public EnfantDTO save(EnfantDTO enfantDTO) {
        LOG.debug("Request to save Enfant : {}", enfantDTO);
        Enfant enfant = enfantMapper.toEntity(enfantDTO);
        enfant = enfantRepository.save(enfant);
        return enfantMapper.toDto(enfant);
    }

    @Override
    public EnfantDTO update(EnfantDTO enfantDTO) {
        LOG.debug("Request to update Enfant : {}", enfantDTO);
        Enfant enfant = enfantMapper.toEntity(enfantDTO);
        enfant = enfantRepository.save(enfant);
        return enfantMapper.toDto(enfant);
    }

    @Override
    public Optional<EnfantDTO> partialUpdate(EnfantDTO enfantDTO) {
        LOG.debug("Request to partially update Enfant : {}", enfantDTO);

        return enfantRepository
            .findById(enfantDTO.getId())
            .map(existingEnfant -> {
                enfantMapper.partialUpdate(existingEnfant, enfantDTO);

                return existingEnfant;
            })
            .map(enfantRepository::save)
            .map(enfantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnfantDTO> findAll() {
        LOG.debug("Request to get all Enfants");
        return enfantRepository.findAll().stream().map(enfantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnfantDTO> findOne(Long id) {
        LOG.debug("Request to get Enfant : {}", id);
        return enfantRepository.findById(id).map(enfantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Enfant : {}", id);
        enfantRepository.deleteById(id);
    }
}

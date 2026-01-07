package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RechercheSolvable;
import com.mycompany.myapp.repository.RechercheSolvableRepository;
import com.mycompany.myapp.service.RechercheSolvableService;
import com.mycompany.myapp.service.dto.RechercheSolvableDTO;
import com.mycompany.myapp.service.mapper.RechercheSolvableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.RechercheSolvable}.
 */
@Service
@Transactional
public class RechercheSolvableServiceImpl implements RechercheSolvableService {

    private static final Logger LOG = LoggerFactory.getLogger(RechercheSolvableServiceImpl.class);

    private final RechercheSolvableRepository rechercheSolvableRepository;

    private final RechercheSolvableMapper rechercheSolvableMapper;

    public RechercheSolvableServiceImpl(
        RechercheSolvableRepository rechercheSolvableRepository,
        RechercheSolvableMapper rechercheSolvableMapper
    ) {
        this.rechercheSolvableRepository = rechercheSolvableRepository;
        this.rechercheSolvableMapper = rechercheSolvableMapper;
    }

    @Override
    public RechercheSolvableDTO save(RechercheSolvableDTO rechercheSolvableDTO) {
        LOG.debug("Request to save RechercheSolvable : {}", rechercheSolvableDTO);
        RechercheSolvable rechercheSolvable = rechercheSolvableMapper.toEntity(rechercheSolvableDTO);
        rechercheSolvable = rechercheSolvableRepository.save(rechercheSolvable);
        return rechercheSolvableMapper.toDto(rechercheSolvable);
    }

    @Override
    public RechercheSolvableDTO update(RechercheSolvableDTO rechercheSolvableDTO) {
        LOG.debug("Request to update RechercheSolvable : {}", rechercheSolvableDTO);
        RechercheSolvable rechercheSolvable = rechercheSolvableMapper.toEntity(rechercheSolvableDTO);
        rechercheSolvable = rechercheSolvableRepository.save(rechercheSolvable);
        return rechercheSolvableMapper.toDto(rechercheSolvable);
    }

    @Override
    public Optional<RechercheSolvableDTO> partialUpdate(RechercheSolvableDTO rechercheSolvableDTO) {
        LOG.debug("Request to partially update RechercheSolvable : {}", rechercheSolvableDTO);

        return rechercheSolvableRepository
            .findById(rechercheSolvableDTO.getId())
            .map(existingRechercheSolvable -> {
                rechercheSolvableMapper.partialUpdate(existingRechercheSolvable, rechercheSolvableDTO);

                return existingRechercheSolvable;
            })
            .map(rechercheSolvableRepository::save)
            .map(rechercheSolvableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RechercheSolvableDTO> findAll() {
        LOG.debug("Request to get all RechercheSolvables");
        return rechercheSolvableRepository
            .findAll()
            .stream()
            .map(rechercheSolvableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RechercheSolvableDTO> findOne(Long id) {
        LOG.debug("Request to get RechercheSolvable : {}", id);
        return rechercheSolvableRepository.findById(id).map(rechercheSolvableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RechercheSolvable : {}", id);
        rechercheSolvableRepository.deleteById(id);
    }
}

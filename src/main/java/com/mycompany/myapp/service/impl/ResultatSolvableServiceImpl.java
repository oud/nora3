package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ResultatSolvable;
import com.mycompany.myapp.repository.ResultatSolvableRepository;
import com.mycompany.myapp.service.ResultatSolvableService;
import com.mycompany.myapp.service.dto.ResultatSolvableDTO;
import com.mycompany.myapp.service.mapper.ResultatSolvableMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ResultatSolvable}.
 */
@Service
@Transactional
public class ResultatSolvableServiceImpl implements ResultatSolvableService {

    private static final Logger LOG = LoggerFactory.getLogger(ResultatSolvableServiceImpl.class);

    private final ResultatSolvableRepository resultatSolvableRepository;

    private final ResultatSolvableMapper resultatSolvableMapper;

    public ResultatSolvableServiceImpl(
        ResultatSolvableRepository resultatSolvableRepository,
        ResultatSolvableMapper resultatSolvableMapper
    ) {
        this.resultatSolvableRepository = resultatSolvableRepository;
        this.resultatSolvableMapper = resultatSolvableMapper;
    }

    @Override
    public ResultatSolvableDTO save(ResultatSolvableDTO resultatSolvableDTO) {
        LOG.debug("Request to save ResultatSolvable : {}", resultatSolvableDTO);
        ResultatSolvable resultatSolvable = resultatSolvableMapper.toEntity(resultatSolvableDTO);
        resultatSolvable = resultatSolvableRepository.save(resultatSolvable);
        return resultatSolvableMapper.toDto(resultatSolvable);
    }

    @Override
    public ResultatSolvableDTO update(ResultatSolvableDTO resultatSolvableDTO) {
        LOG.debug("Request to update ResultatSolvable : {}", resultatSolvableDTO);
        ResultatSolvable resultatSolvable = resultatSolvableMapper.toEntity(resultatSolvableDTO);
        resultatSolvable = resultatSolvableRepository.save(resultatSolvable);
        return resultatSolvableMapper.toDto(resultatSolvable);
    }

    @Override
    public Optional<ResultatSolvableDTO> partialUpdate(ResultatSolvableDTO resultatSolvableDTO) {
        LOG.debug("Request to partially update ResultatSolvable : {}", resultatSolvableDTO);

        return resultatSolvableRepository
            .findById(resultatSolvableDTO.getId())
            .map(existingResultatSolvable -> {
                resultatSolvableMapper.partialUpdate(existingResultatSolvable, resultatSolvableDTO);

                return existingResultatSolvable;
            })
            .map(resultatSolvableRepository::save)
            .map(resultatSolvableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultatSolvableDTO> findAll() {
        LOG.debug("Request to get all ResultatSolvables");
        return resultatSolvableRepository
            .findAll()
            .stream()
            .map(resultatSolvableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultatSolvableDTO> findOne(Long id) {
        LOG.debug("Request to get ResultatSolvable : {}", id);
        return resultatSolvableRepository.findById(id).map(resultatSolvableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ResultatSolvable : {}", id);
        resultatSolvableRepository.deleteById(id);
    }
}

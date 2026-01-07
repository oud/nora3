package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Defaillance;
import com.mycompany.myapp.repository.DefaillanceRepository;
import com.mycompany.myapp.service.DefaillanceService;
import com.mycompany.myapp.service.dto.DefaillanceDTO;
import com.mycompany.myapp.service.mapper.DefaillanceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Defaillance}.
 */
@Service
@Transactional
public class DefaillanceServiceImpl implements DefaillanceService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaillanceServiceImpl.class);

    private final DefaillanceRepository defaillanceRepository;

    private final DefaillanceMapper defaillanceMapper;

    public DefaillanceServiceImpl(DefaillanceRepository defaillanceRepository, DefaillanceMapper defaillanceMapper) {
        this.defaillanceRepository = defaillanceRepository;
        this.defaillanceMapper = defaillanceMapper;
    }

    @Override
    public DefaillanceDTO save(DefaillanceDTO defaillanceDTO) {
        LOG.debug("Request to save Defaillance : {}", defaillanceDTO);
        Defaillance defaillance = defaillanceMapper.toEntity(defaillanceDTO);
        defaillance = defaillanceRepository.save(defaillance);
        return defaillanceMapper.toDto(defaillance);
    }

    @Override
    public DefaillanceDTO update(DefaillanceDTO defaillanceDTO) {
        LOG.debug("Request to update Defaillance : {}", defaillanceDTO);
        Defaillance defaillance = defaillanceMapper.toEntity(defaillanceDTO);
        defaillance = defaillanceRepository.save(defaillance);
        return defaillanceMapper.toDto(defaillance);
    }

    @Override
    public Optional<DefaillanceDTO> partialUpdate(DefaillanceDTO defaillanceDTO) {
        LOG.debug("Request to partially update Defaillance : {}", defaillanceDTO);

        return defaillanceRepository
            .findById(defaillanceDTO.getId())
            .map(existingDefaillance -> {
                defaillanceMapper.partialUpdate(existingDefaillance, defaillanceDTO);

                return existingDefaillance;
            })
            .map(defaillanceRepository::save)
            .map(defaillanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DefaillanceDTO> findAll() {
        LOG.debug("Request to get all Defaillances");
        return defaillanceRepository.findAll().stream().map(defaillanceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DefaillanceDTO> findOne(Long id) {
        LOG.debug("Request to get Defaillance : {}", id);
        return defaillanceRepository.findById(id).map(defaillanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Defaillance : {}", id);
        defaillanceRepository.deleteById(id);
    }
}

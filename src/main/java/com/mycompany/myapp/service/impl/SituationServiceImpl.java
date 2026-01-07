package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Situation;
import com.mycompany.myapp.repository.SituationRepository;
import com.mycompany.myapp.service.SituationService;
import com.mycompany.myapp.service.dto.SituationDTO;
import com.mycompany.myapp.service.mapper.SituationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Situation}.
 */
@Service
@Transactional
public class SituationServiceImpl implements SituationService {

    private static final Logger LOG = LoggerFactory.getLogger(SituationServiceImpl.class);

    private final SituationRepository situationRepository;

    private final SituationMapper situationMapper;

    public SituationServiceImpl(SituationRepository situationRepository, SituationMapper situationMapper) {
        this.situationRepository = situationRepository;
        this.situationMapper = situationMapper;
    }

    @Override
    public SituationDTO save(SituationDTO situationDTO) {
        LOG.debug("Request to save Situation : {}", situationDTO);
        Situation situation = situationMapper.toEntity(situationDTO);
        situation = situationRepository.save(situation);
        return situationMapper.toDto(situation);
    }

    @Override
    public SituationDTO update(SituationDTO situationDTO) {
        LOG.debug("Request to update Situation : {}", situationDTO);
        Situation situation = situationMapper.toEntity(situationDTO);
        situation = situationRepository.save(situation);
        return situationMapper.toDto(situation);
    }

    @Override
    public Optional<SituationDTO> partialUpdate(SituationDTO situationDTO) {
        LOG.debug("Request to partially update Situation : {}", situationDTO);

        return situationRepository
            .findById(situationDTO.getId())
            .map(existingSituation -> {
                situationMapper.partialUpdate(existingSituation, situationDTO);

                return existingSituation;
            })
            .map(situationRepository::save)
            .map(situationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SituationDTO> findAll() {
        LOG.debug("Request to get all Situations");
        return situationRepository.findAll().stream().map(situationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SituationDTO> findOne(Long id) {
        LOG.debug("Request to get Situation : {}", id);
        return situationRepository.findById(id).map(situationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Situation : {}", id);
        situationRepository.deleteById(id);
    }
}

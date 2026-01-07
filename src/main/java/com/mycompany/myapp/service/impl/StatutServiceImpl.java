package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Statut;
import com.mycompany.myapp.repository.StatutRepository;
import com.mycompany.myapp.service.StatutService;
import com.mycompany.myapp.service.dto.StatutDTO;
import com.mycompany.myapp.service.mapper.StatutMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Statut}.
 */
@Service
@Transactional
public class StatutServiceImpl implements StatutService {

    private static final Logger LOG = LoggerFactory.getLogger(StatutServiceImpl.class);

    private final StatutRepository statutRepository;

    private final StatutMapper statutMapper;

    public StatutServiceImpl(StatutRepository statutRepository, StatutMapper statutMapper) {
        this.statutRepository = statutRepository;
        this.statutMapper = statutMapper;
    }

    @Override
    public StatutDTO save(StatutDTO statutDTO) {
        LOG.debug("Request to save Statut : {}", statutDTO);
        Statut statut = statutMapper.toEntity(statutDTO);
        statut = statutRepository.save(statut);
        return statutMapper.toDto(statut);
    }

    @Override
    public StatutDTO update(StatutDTO statutDTO) {
        LOG.debug("Request to update Statut : {}", statutDTO);
        Statut statut = statutMapper.toEntity(statutDTO);
        statut = statutRepository.save(statut);
        return statutMapper.toDto(statut);
    }

    @Override
    public Optional<StatutDTO> partialUpdate(StatutDTO statutDTO) {
        LOG.debug("Request to partially update Statut : {}", statutDTO);

        return statutRepository
            .findById(statutDTO.getId())
            .map(existingStatut -> {
                statutMapper.partialUpdate(existingStatut, statutDTO);

                return existingStatut;
            })
            .map(statutRepository::save)
            .map(statutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatutDTO> findAll() {
        LOG.debug("Request to get all Statuts");
        return statutRepository.findAll().stream().map(statutMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatutDTO> findOne(Long id) {
        LOG.debug("Request to get Statut : {}", id);
        return statutRepository.findById(id).map(statutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Statut : {}", id);
        statutRepository.deleteById(id);
    }
}

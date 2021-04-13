package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Maestro;
import com.mycompany.myapp.repository.MaestroRepository;
import com.mycompany.myapp.service.MaestroService;
import com.mycompany.myapp.service.dto.MaestroDTO;
import com.mycompany.myapp.service.mapper.MaestroMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Maestro}.
 */
@Service
@Transactional
public class MaestroServiceImpl implements MaestroService {

    private final Logger log = LoggerFactory.getLogger(MaestroServiceImpl.class);

    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    public MaestroServiceImpl(MaestroRepository maestroRepository, MaestroMapper maestroMapper) {
        this.maestroRepository = maestroRepository;
        this.maestroMapper = maestroMapper;
    }

    @Override
    public MaestroDTO save(MaestroDTO maestroDTO) {
        log.debug("Request to save Maestro : {}", maestroDTO);
        Maestro maestro = maestroMapper.toEntity(maestroDTO);
        maestro = maestroRepository.save(maestro);
        return maestroMapper.toDto(maestro);
    }

    @Override
    public Optional<MaestroDTO> partialUpdate(MaestroDTO maestroDTO) {
        log.debug("Request to partially update Maestro : {}", maestroDTO);

        return maestroRepository
            .findById(maestroDTO.getId())
            .map(
                existingMaestro -> {
                    maestroMapper.partialUpdate(existingMaestro, maestroDTO);
                    return existingMaestro;
                }
            )
            .map(maestroRepository::save)
            .map(maestroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaestroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Maestros");
        return maestroRepository.findAll(pageable).map(maestroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaestroDTO> findOne(Long id) {
        log.debug("Request to get Maestro : {}", id);
        return maestroRepository.findById(id).map(maestroMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Maestro : {}", id);
        maestroRepository.deleteById(id);
    }
}

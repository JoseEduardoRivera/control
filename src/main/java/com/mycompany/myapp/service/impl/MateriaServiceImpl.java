package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Materia;
import com.mycompany.myapp.repository.MateriaRepository;
import com.mycompany.myapp.service.MateriaService;
import com.mycompany.myapp.service.dto.MateriaDTO;
import com.mycompany.myapp.service.mapper.MateriaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Materia}.
 */
@Service
@Transactional
public class MateriaServiceImpl implements MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaServiceImpl.class);

    private final MateriaRepository materiaRepository;

    private final MateriaMapper materiaMapper;

    public MateriaServiceImpl(MateriaRepository materiaRepository, MateriaMapper materiaMapper) {
        this.materiaRepository = materiaRepository;
        this.materiaMapper = materiaMapper;
    }

    @Override
    public MateriaDTO save(MateriaDTO materiaDTO) {
        log.debug("Request to save Materia : {}", materiaDTO);
        Materia materia = materiaMapper.toEntity(materiaDTO);
        materia = materiaRepository.save(materia);
        return materiaMapper.toDto(materia);
    }

    @Override
    public Optional<MateriaDTO> partialUpdate(MateriaDTO materiaDTO) {
        log.debug("Request to partially update Materia : {}", materiaDTO);

        return materiaRepository
            .findById(materiaDTO.getId())
            .map(
                existingMateria -> {
                    materiaMapper.partialUpdate(existingMateria, materiaDTO);
                    return existingMateria;
                }
            )
            .map(materiaRepository::save)
            .map(materiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MateriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materias");
        return materiaRepository.findAll(pageable).map(materiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MateriaDTO> findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findById(id).map(materiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.deleteById(id);
    }
}

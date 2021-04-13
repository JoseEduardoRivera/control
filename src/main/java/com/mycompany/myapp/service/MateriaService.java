package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MateriaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Materia}.
 */
public interface MateriaService {
    /**
     * Save a materia.
     *
     * @param materiaDTO the entity to save.
     * @return the persisted entity.
     */
    MateriaDTO save(MateriaDTO materiaDTO);

    /**
     * Partially updates a materia.
     *
     * @param materiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MateriaDTO> partialUpdate(MateriaDTO materiaDTO);

    /**
     * Get all the materias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MateriaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" materia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MateriaDTO> findOne(Long id);

    /**
     * Delete the "id" materia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

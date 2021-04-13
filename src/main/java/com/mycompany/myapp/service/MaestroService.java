package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MaestroDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Maestro}.
 */
public interface MaestroService {
    /**
     * Save a maestro.
     *
     * @param maestroDTO the entity to save.
     * @return the persisted entity.
     */
    MaestroDTO save(MaestroDTO maestroDTO);

    /**
     * Partially updates a maestro.
     *
     * @param maestroDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaestroDTO> partialUpdate(MaestroDTO maestroDTO);

    /**
     * Get all the maestros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MaestroDTO> findAll(Pageable pageable);

    /**
     * Get the "id" maestro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaestroDTO> findOne(Long id);

    /**
     * Delete the "id" maestro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

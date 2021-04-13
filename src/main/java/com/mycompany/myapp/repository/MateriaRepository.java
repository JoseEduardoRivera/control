package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Materia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long>, JpaSpecificationExecutor<Materia> {}

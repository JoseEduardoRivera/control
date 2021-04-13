package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Maestro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Maestro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaestroRepository extends JpaRepository<Maestro, Long>, JpaSpecificationExecutor<Maestro> {}

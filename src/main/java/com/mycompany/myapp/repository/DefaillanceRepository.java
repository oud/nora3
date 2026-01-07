package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Defaillance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Defaillance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefaillanceRepository extends JpaRepository<Defaillance, Long> {}

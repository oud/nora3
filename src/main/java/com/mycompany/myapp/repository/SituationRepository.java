package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Situation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Situation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {}

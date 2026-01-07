package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Demarche;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Demarche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemarcheRepository extends JpaRepository<Demarche, Long> {}

package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Creancier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Creancier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreancierRepository extends JpaRepository<Creancier, Long> {}

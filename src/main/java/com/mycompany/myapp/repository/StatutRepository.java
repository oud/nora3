package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Statut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Statut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {}

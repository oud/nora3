package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RechercheSolvable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RechercheSolvable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RechercheSolvableRepository extends JpaRepository<RechercheSolvable, Long> {}

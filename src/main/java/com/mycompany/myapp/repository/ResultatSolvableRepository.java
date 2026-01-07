package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResultatSolvable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResultatSolvable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatSolvableRepository extends JpaRepository<ResultatSolvable, Long> {}

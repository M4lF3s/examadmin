package de.fsmpi.examadmin.repository;

import de.fsmpi.examadmin.domain.Dozent;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Dozent entity.
 */
@SuppressWarnings("unused")
public interface DozentRepository extends JpaRepository<Dozent,Long> {

    @Query("select distinct dozent from Dozent dozent left join fetch dozent.faches")
    List<Dozent> findAllWithEagerRelationships();

    @Query("select dozent from Dozent dozent left join fetch dozent.faches where dozent.id =:id")
    Dozent findOneWithEagerRelationships(@Param("id") Long id);

}

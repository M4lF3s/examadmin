package de.fsmpi.examadmin.repository;

import de.fsmpi.examadmin.domain.Fach;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fach entity.
 */
@SuppressWarnings("unused")
public interface FachRepository extends JpaRepository<Fach,Long> {

}

package de.fsmpi.examadmin.repository;

import de.fsmpi.examadmin.domain.Klausur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Klausur entity.
 */
@SuppressWarnings("unused")
public interface KlausurRepository extends JpaRepository<Klausur,Long> {

}

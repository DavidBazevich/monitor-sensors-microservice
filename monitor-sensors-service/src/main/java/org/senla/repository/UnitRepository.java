package org.senla.repository;

import org.senla.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Units, Integer> {
    Optional<Units> findByName(String type);
}

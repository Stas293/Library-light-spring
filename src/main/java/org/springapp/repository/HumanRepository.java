package org.springapp.repository;

import org.springapp.models.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {
    Optional<Human> findByFirstNameAndLastName(String firstName, String lastName);
}

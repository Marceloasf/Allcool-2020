package br.com.allcool.person.repository;

import br.com.allcool.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

	Optional<Person> findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
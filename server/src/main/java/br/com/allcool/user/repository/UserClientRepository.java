package br.com.allcool.user.repository;

import br.com.allcool.user.domain.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserClientRepository extends JpaRepository<UserClient, UUID> {
	
	Optional<UserClient> findByPersonId(UUID id);
}

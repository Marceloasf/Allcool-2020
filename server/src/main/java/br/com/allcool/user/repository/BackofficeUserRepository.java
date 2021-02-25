package br.com.allcool.user.repository;

import br.com.allcool.user.domain.BackofficeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BackofficeUserRepository extends JpaRepository<BackofficeUser, UUID> {
}

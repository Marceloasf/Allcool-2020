package br.com.allcool.container.repository;

import br.com.allcool.container.domain.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContainerRepository extends JpaRepository<Container, UUID> {
}

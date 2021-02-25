package br.com.allcool.file.repository;

import br.com.allcool.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}

package br.com.allcool.publication.repository;

import br.com.allcool.publication.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {

    List<Publication> findAllByReviewUserId(UUID userId);
}

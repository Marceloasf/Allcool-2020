package br.com.allcool.review.repository;

import br.com.allcool.review.domain.ReviewProductFlavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewProductFlavorRepository extends JpaRepository<ReviewProductFlavor, UUID> {
}

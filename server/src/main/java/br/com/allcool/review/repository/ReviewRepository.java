package br.com.allcool.review.repository;

import br.com.allcool.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findAllByProductId(UUID productId);

    boolean existsByUserIdAndProductId(UUID userId, UUID productId);
}

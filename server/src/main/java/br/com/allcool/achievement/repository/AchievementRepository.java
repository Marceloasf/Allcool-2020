package br.com.allcool.achievement.repository;

import br.com.allcool.achievement.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AchievementRepository extends JpaRepository<Achievement, UUID> {

    long countByProductId(UUID productId);

    List<Achievement> findAllAchievementByProductId(UUID productId);

    List<Achievement> findAllByProductTypeId(UUID productTypeId);

    List<Achievement> findAllByBrandId(UUID brandId);

}

package br.com.allcool.achievement.repository;


import br.com.allcool.achievement.domain.AchievementProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementProductTypeRepository extends JpaRepository<AchievementProductType, UUID> {
}

package br.com.allcool.user.repository;

import br.com.allcool.user.domain.UserClientAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserClientAchievementRepository extends JpaRepository<UserClientAchievement, UUID> {

    long countByUserId(UUID userId);
}

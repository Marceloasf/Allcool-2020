package br.com.allcool.achievement.service;

import br.com.allcool.user.repository.UserClientAchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserClientAchievementService {

    private final UserClientAchievementRepository userClientAchievementRepository;

    public UserClientAchievementService(UserClientAchievementRepository repository) {
        this.userClientAchievementRepository = repository;
    }

    public Long countByUserId(UUID userId) {

        return userClientAchievementRepository.countByUserId(userId);
    }

}

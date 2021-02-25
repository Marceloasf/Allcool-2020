package br.com.allcool.achievement.resource;

import br.com.allcool.achievement.service.UserClientAchievementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/achievements-users")
public class UserClientAchievementResource {

    private final UserClientAchievementService service;

    public UserClientAchievementResource(UserClientAchievementService service) {
        this.service = service;
    }

    @GetMapping("{userId}")
    public Long countByUserId(UUID userId) {

        return service.countByUserId(userId);
    }
}

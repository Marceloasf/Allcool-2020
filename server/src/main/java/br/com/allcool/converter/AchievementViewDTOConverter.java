package br.com.allcool.converter;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.dto.AchievementViewDTO;

import static java.util.Objects.isNull;

public class AchievementViewDTOConverter {

    public AchievementViewDTO to(Achievement achievement) {

        AchievementViewDTO dto = new AchievementViewDTO();

        if (isNull(achievement)) {
            return dto;
        }

        dto.setId(achievement.getId());
        dto.setAchievemantFileUrl(achievement.getFile().getUrl());
        dto.setDescription(achievement.getDescription());
        dto.setTitle(achievement.getTitle());

        return dto;
    }

}

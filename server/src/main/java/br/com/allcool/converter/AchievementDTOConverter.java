package br.com.allcool.converter;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.dto.AchievementDTO;

import static java.util.Objects.isNull;

public class AchievementDTOConverter {

    public AchievementDTO toBrandAchievement(Achievement achievement) {

        AchievementDTO dto = new AchievementDTO();

        if (isNull(achievement)) {
            return dto;
        }

        dto.setId(achievement.getId());
        dto.setBrand(achievement.getBrand().getName());
        dto.setBadgeUrl(achievement.getFile().getUrl());
        dto.setAchievementName(achievement.getTitle());
        dto.setDescription(achievement.getDescription());
        dto.setType(achievement.getType());

        return dto;
    }

    public AchievementDTO toProductAchievement(Achievement achievement) {

        AchievementDTO dto = new AchievementDTO();

        dto.setBrand(achievement.getProduct().getBrand().getName());
        dto.setId(achievement.getId());
        dto.setBrand(achievement.getBrand().getName());
        dto.setBadgeUrl(achievement.getFile().getUrl());
        dto.setAchievementName(achievement.getTitle());
        dto.setDescription(achievement.getDescription());
        dto.setType(achievement.getType());

        return dto;
    }

    public AchievementDTO toProductTypeAchievement(Achievement achievement) {

        AchievementDTO dto = new AchievementDTO();

        dto.setBrand(achievement.getProduct().getBrand().getName());
        dto.setId(achievement.getId());
        dto.setBrand(achievement.getBrand().getName());
        dto.setBadgeUrl(achievement.getFile().getUrl());
        dto.setAchievementName(achievement.getTitle());
        dto.setDescription(achievement.getDescription());
        dto.setType(achievement.getType());

        return dto;
    }


}

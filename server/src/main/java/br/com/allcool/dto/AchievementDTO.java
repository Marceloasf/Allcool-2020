package br.com.allcool.dto;

import br.com.allcool.enums.AchievementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {

    private UUID id;
    private String brand;
    private String badgeUrl;
    private String achievementName;
    private String description;
    private AchievementTypeEnum type;
}

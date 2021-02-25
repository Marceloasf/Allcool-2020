package br.com.allcool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementViewDTO {

    private UUID id;
    private String achievemantFileUrl;
    private String title;
    private String description;
}

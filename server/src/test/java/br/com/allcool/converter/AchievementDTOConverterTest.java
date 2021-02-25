package br.com.allcool.converter;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.brand.domain.Brand;
import br.com.allcool.dto.AchievementDTO;
import br.com.allcool.enums.AchievementTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AchievementDTOConverterTest {

    private final AchievementDTOConverter achievementDTOConverter = new AchievementDTOConverter();

    @Test
    public void toBrandAchievement() {

        File file = new File();
        file.setUrl("www.testefile.com");

        Brand brand = new Brand();
        brand.setName("Patagonia");

        Product product = new Product();
        product.setBrand(brand);
        product.setName("Patagonia Amber Lager");

        Achievement achievement = new Achievement();
        achievement.setId(UUID.randomUUID());
        achievement.setBrand(brand);
        achievement.setTitle("Novato");
        achievement.setDescription("Parabéns pela sua primeira conquista!");
        achievement.setProduct(product);
        achievement.setType(AchievementTypeEnum.PRODUCT);
        achievement.setFile(file);

        AchievementDTO dto = this.achievementDTOConverter.toBrandAchievement(achievement);

        assertThat(dto.getId()).isEqualTo(achievement.getId());
        assertThat(dto.getBrand()).isEqualTo(achievement.getBrand().getName());
        assertThat(dto.getAchievementName()).isEqualTo(achievement.getTitle());
        assertThat(dto.getDescription()).isEqualTo(achievement.getDescription());
        assertThat(dto.getBadgeUrl()).isEqualTo(achievement.getFile().getUrl());
        assertThat(dto.getType()).isEqualTo(achievement.getType());
    }
}

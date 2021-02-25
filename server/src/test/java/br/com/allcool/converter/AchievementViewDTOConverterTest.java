package br.com.allcool.converter;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.brand.domain.Brand;
import br.com.allcool.dto.AchievementViewDTO;
import br.com.allcool.enums.AchievementTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AchievementViewDTOConverterTest {

    private final AchievementViewDTOConverter achievementViewDTOConverter = new AchievementViewDTOConverter();

    @Test
    public void to() {

        File file = new File();
        file.setUrl("www.testefile.com");

        Brand brand = new Brand();
        brand.setName("Patagonia");

        Product product = new Product();
        product.setBrand(brand);
        product.setName("Patagonia Amber Lager");

        Achievement achievement = new Achievement();
        achievement.setId(UUID.randomUUID());
        achievement.setTitle("Novato");
        achievement.setDescription("Parabéns pela sua primeira conquista!");
        achievement.setProduct(product);
        achievement.setType(AchievementTypeEnum.PRODUCT);
        achievement.setFile(file);

        AchievementViewDTO dto = this.achievementViewDTOConverter.to(achievement);

        assertThat(dto.getId()).isEqualTo(achievement.getId());
        assertThat(dto.getTitle()).isEqualTo(achievement.getTitle());
        assertThat(dto.getDescription()).isEqualTo(achievement.getDescription());
        assertThat(dto.getAchievemantFileUrl()).isEqualTo(file.getUrl());

    }
}

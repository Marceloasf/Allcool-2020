package br.com.allcool.achievement.service;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.achievement.repository.AchievementRepository;
import br.com.allcool.brand.domain.Brand;
import br.com.allcool.dto.AchievementDTO;
import br.com.allcool.enums.AchievementTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AchievementServiceTest {

    @InjectMocks
    private AchievementService service;

    @Mock
    private AchievementRepository repository;

    @Test
    public void findAllAchievementByProductId() {

        File file = new File();
        file.setUrl("www.testefile.com");

        Brand brand = new Brand();
        brand.setName("Patagonia");

        Product product = new Product();
        product.setName("Patagonia Amber Lager");

        Achievement achievement = new Achievement();
        achievement.setId(UUID.randomUUID());
        achievement.setBrand(brand);
        achievement.setTitle("Novato");
        achievement.setDescription("Parabens pela sua primeira conquista!");
        achievement.setProduct(product);
        achievement.setType(AchievementTypeEnum.PRODUCT);
        achievement.setFile(file);

        when(this.repository.findAllAchievementByProductId(achievement.getId())).thenReturn(Collections
                .singletonList(achievement));

        List<AchievementDTO> result = this.service.findAllAchievementByProductId(achievement.getId());

        assertThat(result).extracting(AchievementDTO::getId).containsExactly(achievement.getId());
        assertThat(result).extracting(AchievementDTO::getBrand).containsExactly(brand.getName());
        assertThat(result).extracting(AchievementDTO::getAchievementName).containsExactly(achievement.getTitle());
        assertThat(result).extracting(AchievementDTO::getDescription).containsExactly(achievement.getDescription());
        assertThat(result).extracting(AchievementDTO::getBadgeUrl).containsExactly(achievement.getFile().getUrl());
        assertThat(result).extracting(AchievementDTO::getType).containsExactly(achievement.getType());

        verify(this.repository).findAllAchievementByProductId(achievement.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findAllByProductTypeId() {

        UUID productTypeId = UUID.randomUUID();

        when(this.repository.findAllByProductTypeId(productTypeId)).thenReturn(new ArrayList<>());

        this.service.findAllByProductTypeId(productTypeId);

        verify(this.repository).findAllByProductTypeId(productTypeId);
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void findAllByBrandId() {

        UUID brandId = UUID.randomUUID();

        when(this.repository.findAllByBrandId(brandId)).thenReturn(new ArrayList<>());

        this.service.findAllByBrandId(brandId);

        verify(this.repository).findAllByBrandId(brandId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findById() {

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
        achievement.setDescription("Parabens pela sua primeira conquista!");
        achievement.setProduct(product);
        achievement.setType(AchievementTypeEnum.PRODUCT);
        achievement.setFile(file);

        when(this.repository.findById(achievement.getId())).thenReturn(Optional.of(achievement));

        this.service.findById(achievement.getId());

        verify(this.repository).findById(achievement.getId());
        verifyNoMoreInteractions(this.repository);

    }
   @Test
   public void countByProductId() {

       UUID productId = UUID.randomUUID();

       when(this.repository.countByProductId(productId)).thenReturn(1L);

       this.service.countByProductId(productId);

       verify(this.repository).countByProductId(productId);
       verifyNoMoreInteractions(this.repository);
   }

}

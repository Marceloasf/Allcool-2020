package br.com.allcool.achievement.resource;

import br.com.allcool.achievement.service.AchievementService;
import br.com.allcool.dto.AchievementDTO;
import br.com.allcool.dto.AchievementViewDTO;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(AchievementResource.class)
public class AchievementResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService service;

    @Test
    public void findAllAchievementByProductId() throws Exception {

        AchievementDTO achievement1DTO = new AchievementDTO();

        achievement1DTO.setId(UUID.randomUUID());
        achievement1DTO.setBrand("Becks");
        achievement1DTO.setBadgeUrl("5_cervejas_Ambev");
        achievement1DTO.setAchievementName("5 Bottles");
        achievement1DTO.setDescription("Parabéns! Você já experimentou 5 cervejas Ambev");

        AchievementDTO achievement2DTO = new AchievementDTO();

        achievement2DTO.setId(UUID.randomUUID());
        achievement2DTO.setBrand("Goose Island");
        achievement2DTO.setBadgeUrl("Rei_da_IPA");
        achievement2DTO.setAchievementName("Rei da IPA");
        achievement2DTO.setDescription("Você é um grande apreciador de IPAs!");

        List<AchievementDTO> achievementDTOList = new ArrayList<>();
        achievementDTOList.add(achievement1DTO);
        achievementDTOList.add(achievement2DTO);

        UUID productId = UUID.randomUUID();

        when(this.service.findAllAchievementByProductId(productId)).thenReturn(achievementDTOList);

        this.mockMvc.perform(get("/api/achievements/{productId}", productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(achievement1DTO.getId().toString(),
                        achievement2DTO.getId().toString())))
                .andExpect(jsonPath("$.[*].brand", hasItems(achievement1DTO.getBrand(),
                        achievement2DTO.getBrand())))
                .andExpect(jsonPath("$.[*].badgeUrl", hasItems(achievement1DTO.getBadgeUrl(),
                        achievement2DTO.getBadgeUrl())))
                .andExpect(jsonPath("$.[*].achievementName", hasItems(achievement1DTO.getAchievementName(),
                        achievement2DTO.getAchievementName())))
                .andExpect(jsonPath("$.[*].description", hasItems(achievement1DTO.getDescription(),
                        achievement2DTO.getDescription())))
                .andExpect(jsonPath("$.[*].type", hasItems(achievement1DTO.getType(),
                        achievement2DTO.getType())));

        verify(this.service).findAllAchievementByProductId(productId);
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void findbyid() throws Exception {

        AchievementViewDTO achievementViewDTO = new AchievementViewDTO();
        achievementViewDTO.setId(UUID.randomUUID());
        achievementViewDTO.setTitle("Mestre Cervejeiro");
        achievementViewDTO.setDescription("Dominação total da cerveja, não somos dignos. São mais de 200 cervejas diferentes!");
        achievementViewDTO.setAchievemantFileUrl("www.teste.com");

        when(this.service.findById(achievementViewDTO.getId())).thenReturn(achievementViewDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/achievements/{id}/view", achievementViewDTO.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(achievementViewDTO.getId().toString())))
                .andExpect(jsonPath("$.title", equalTo(achievementViewDTO.getTitle())))
                .andExpect(jsonPath("$.description", equalTo(achievementViewDTO.getDescription())))
                .andExpect(jsonPath("$.achievemantFileUrl", equalTo(achievementViewDTO.getAchievemantFileUrl())));

        verify(this.service).findById(achievementViewDTO.getId());
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void findAllByProductTypeId() throws Exception {

        UUID productTypeId = UUID.randomUUID();

        when(this.service.findAllByProductTypeId(productTypeId))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/achievements/product-types/{productTypeId}", productTypeId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service).findAllByProductTypeId(productTypeId);
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void findAllByBrandId() throws Exception {

        UUID brandId = UUID.randomUUID();

        when(this.service.findAllByBrandId(brandId))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/achievements/brands/{brandId}", brandId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service).findAllByBrandId(brandId);
        verifyNoMoreInteractions(this.service);
    }

}

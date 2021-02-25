package br.com.allcool.news.resource;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.NewsDTO;
import br.com.allcool.enums.NewsTypeEnum;
import br.com.allcool.news.service.NewsService;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(NewsResource.class)
public class NewsResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService service;

    @Test
    public void findById() throws Exception {

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setPublicPlace("Rua Luiza Zequim");
        address.setLocality("Maringa");
        address.setFederatedUnit("PR");

        NewsDTO news = new NewsDTO();
        news.setId(UUID.randomUUID());
        news.setAddress(address);
        news.setTitle("Titulo News");
        news.setDescription("Descricao teste news");
        news.setRating(BigDecimal.valueOf(4.5));
        news.setPictureUrl("www.teste.com");
        news.setEventDate(LocalDateTime.of(2020,2,5,20,30));
        news.setType(NewsTypeEnum.EVENT);

        when(this.service.findById(news.getId())).thenReturn(news);

        this.mockMvc.perform(get("/api/news/{id}", news.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(news.getId().toString())))
                .andExpect(jsonPath("$.address.id",equalTo(news.getAddress().getId().toString())))
                .andExpect(jsonPath("$.address.publicPlace",equalTo(news.getAddress().getPublicPlace())))
                .andExpect(jsonPath("$.address.locality",equalTo(news.getAddress().getLocality())))
                .andExpect(jsonPath("$.address.federatedUnit",equalTo(news.getAddress().getFederatedUnit())))
                .andExpect(jsonPath("$.title",equalTo(news.getTitle())))
                .andExpect(jsonPath("$.description",equalTo(news.getDescription())))
                .andExpect(jsonPath("$.rating",equalTo(4.5)))
                .andExpect(jsonPath("$.pictureUrl",equalTo(news.getPictureUrl())))
                .andExpect(jsonPath("$.eventDate", notNullValue()))
                .andExpect(jsonPath("$.type",equalTo(news.getType().toString())));

        verify(this.service).findById(news.getId());
        verifyNoMoreInteractions(this.service);
    }
}

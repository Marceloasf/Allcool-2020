package br.com.allcool.publication.resource;

import br.com.allcool.converter.NewsDTOConverter;
import br.com.allcool.converter.ReviewDTOConverter;
import br.com.allcool.dto.PublicationDTO;
import br.com.allcool.enums.PublicationTypeEnum;
import br.com.allcool.news.domain.News;
import br.com.allcool.person.domain.Person;
import br.com.allcool.product.domain.Product;
import br.com.allcool.publication.service.PublicationService;
import br.com.allcool.review.domain.Review;
import br.com.allcool.test.ResourceTest;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(PublicationResource.class)
public class PublicationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicationService service;

    @Test
    public void findAll() throws Exception {

        Person person = new Person();

        UserClient userClient = new UserClient();
        userClient.setPerson(person);

        Product product = new Product();

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setUser(userClient);
        review.setProduct(product);

        News news = new News();
        news.setId(UUID.randomUUID());

        PublicationDTO publicationDTO1 = new PublicationDTO();
        publicationDTO1.setId(UUID.randomUUID());
        publicationDTO1.setReview(new ReviewDTOConverter().to(review));
        publicationDTO1.setType(PublicationTypeEnum.REVIEW);

        PublicationDTO publicationDTO2 = new PublicationDTO();
        publicationDTO2.setId(UUID.randomUUID());
        publicationDTO2.setNews(new NewsDTOConverter().to(news));
        publicationDTO2.setType(PublicationTypeEnum.NEWS);

        when(this.service.findAll()).thenReturn(Arrays.asList(publicationDTO1, publicationDTO2));

        this.mockMvc.perform(get("/api/publications"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id",
                        hasItems(publicationDTO1.getId().toString(), publicationDTO2.getId().toString())))
                .andExpect(jsonPath("$.[*].type",
                        hasItems(publicationDTO1.getType().toString(), publicationDTO2.getType().toString())))
                .andExpect(jsonPath("$.[*].review.id",
                        hasItems(publicationDTO1.getReview().getId().toString())))
                .andExpect(jsonPath("$.[*].news.id",
                        hasItems(publicationDTO2.getNews().getId().toString())));

        verify(this.service).findAll();
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void findAllReviewPublicationsByUserId() throws Exception {

        UUID userId = UUID.randomUUID();

        when(this.service.findAllReviewPublicationsByUserId(userId))
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/publications/{id}", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service).findAllReviewPublicationsByUserId(userId);
        verifyNoMoreInteractions(this.service);
    }
}
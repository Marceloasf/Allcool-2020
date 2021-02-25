package br.com.allcool.review.resource;

import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.dto.ReviewProductFlavorDTO;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.service.ReviewService;
import br.com.allcool.test.ResourceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(ReviewResource.class)
public class ReviewResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService service;

    @Test
    public void findAllByProductId() throws Exception {

        ReviewDTO review1DTO = new ReviewDTO();

        review1DTO.setId(UUID.randomUUID());
        review1DTO.setUserName("Marcelo Silva");
        review1DTO.setProductName("Goose IPA");
        review1DTO.setAvatarUrl("32f5d633-c094-46d8-826e-d799572d7610");
        review1DTO.setDescription("Uma ótima IPA, com aromas cítricos e sabor intenso mas suave.");
        review1DTO.setRating(BigDecimal.valueOf(5));

        ReviewDTO review2DTO = new ReviewDTO();

        review2DTO.setId(UUID.randomUUID());
        review2DTO.setUserName("Pedro Henrique");
        review2DTO.setProductName("Coruja Amber Lager");
        review2DTO.setAvatarUrl("fca353f0-4027-4d13-a2f6-a9ec218720a3");
        review2DTO.setDescription("Bem presente o malte tostado e bastante lúpulo");
        review2DTO.setRating(BigDecimal.valueOf(4));

        List<ReviewDTO> listReviewDTOS = new ArrayList<>();
        listReviewDTOS.add(review1DTO);
        listReviewDTOS.add(review2DTO);

        UUID productId = UUID.randomUUID();

        when(this.service.findAllByProductId(productId)).thenReturn(listReviewDTOS);

        this.mockMvc.perform(get("/api/reviews/{productId}", productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(review1DTO.getId().toString(),
                        review2DTO.getId().toString())))
                .andExpect(jsonPath("$.[*].userName", hasItems(review1DTO.getUserName(),
                        review2DTO.getUserName())))
                .andExpect(jsonPath("$.[*].productName", hasItems(review1DTO.getProductName(),
                        review2DTO.getProductName())))
                .andExpect(jsonPath("$.[*].avatarUrl", hasItems(review1DTO.getAvatarUrl(),
                        review2DTO.getAvatarUrl())))
                .andExpect(jsonPath("$.[*].description", hasItems(review1DTO.getDescription(),
                        review2DTO.getDescription())))
                .andExpect(jsonPath("$.[*].rating", hasItems(5)));

        verify(this.service).findAllByProductId(productId);
        verifyNoMoreInteractions(this.service);

    }

    @Test
    public void saveReview() throws Exception {

        ReviewFormDTO dto = new ReviewFormDTO();

        when(this.service.saveReview(dto)).thenReturn(new Review());

        this.mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(this.service).saveReview(dto);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void isProductReviewed() throws Exception {

        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(this.service.isProductReviewed(userId, productId)).thenReturn(true);

        this.mockMvc.perform(
                get("/api/reviews/products/{productId}/users/{userId}/verify-user-review",
                        productId, userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(true)));

        verify(this.service).isProductReviewed(userId, productId);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void findById() throws Exception {

        ReviewDTO review = new ReviewDTO();
        review.setId(UUID.randomUUID());
        review.setUserName("Thiago Bussola da Silva");
        review.setAvatarUrl("www.avatarurl.com.br");
        review.setDescription("Uma cerveja saborosa");
        review.setPictureUrl("www.foto.com.br");
        review.setProductName("Skoll");
        review.setRating(BigDecimal.valueOf(4.5));
        review.setFlavors(Collections.singletonList(new ReviewProductFlavorDTO()));

        when(this.service.findById(review.getId())).thenReturn(review);

        this.mockMvc.perform(get("/api/reviews/{id}/view", review.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(review.getId().toString())))
                .andExpect(jsonPath("$.userName", equalTo(review.getUserName())))
                .andExpect(jsonPath("$.avatarUrl", equalTo(review.getAvatarUrl())))
                .andExpect(jsonPath("$.description", equalTo(review.getDescription())))
                .andExpect(jsonPath("$.pictureUrl", equalTo(review.getPictureUrl())))
                .andExpect(jsonPath("$.productName", equalTo(review.getProductName())))
                .andExpect(jsonPath("$.rating", equalTo(4.5)))
                .andExpect(jsonPath("$.flavors.size()", equalTo(1)));

        verify(this.service).findById(review.getId());
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void saveReviewPicture() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "fileData",
                "teste.jpeg",
                "text/plain",
                "{\"key1\": \"value1\"}".getBytes());

        UUID id = UUID.randomUUID();

        doNothing().when(this.service).saveReviewPicture(mockMultipartFile, id);

        this.mockMvc.perform(multipart("/api/reviews/{reviewId}/upload-picture", id)
                .file("image", mockMultipartFile.getBytes())
                .characterEncoding("UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(this.service).saveReviewPicture(any(), any());
        verifyNoMoreInteractions(this.service);
    }
}
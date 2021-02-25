package br.com.allcool.review.service;

import br.com.allcool.converter.ReviewFormDTOConverter;
import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.exception.CreationNotPermittedException;
import br.com.allcool.exception.DataNotFoundException;
import br.com.allcool.file.domain.File;
import br.com.allcool.file.service.FileService;
import br.com.allcool.person.domain.Person;
import br.com.allcool.product.domain.Product;
import br.com.allcool.publication.domain.Publication;
import br.com.allcool.publication.repository.PublicationRepository;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.repository.ReviewRepository;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

    private final ReviewFormDTOConverter dtoConverter = new ReviewFormDTOConverter();

    @InjectMocks
    private ReviewService service;

    @Mock
    private ReviewRepository repository;

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private FileService fileService;

    @Test
    public void findAllByProductId() {

        File file = new File();
        file.setUrl("www.teste.com.br");

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Claudinho");

        UserClient userClient = new UserClient();
        userClient.setPerson(person);
        userClient.setFile(file);

        Product product = new Product();
        product.setName("Brahma Extra");

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setDescription("Uma cerveja muito boa!");
        review.setRating(BigDecimal.valueOf(5));
        review.setUser(userClient);
        review.setProduct(product);
        review.setFile(file);

        when(this.repository.findAllByProductId(review.getId())).thenReturn(Collections.singletonList(review));

        List<ReviewDTO> result = this.service.findAllByProductId(review.getId());

        assertThat(result).extracting(ReviewDTO::getId).containsExactly(review.getId());
        assertThat(result).extracting(ReviewDTO::getUserName).containsExactly(userClient.getPerson().getName());
        assertThat(result).extracting(ReviewDTO::getProductName).containsExactly(product.getName());
        assertThat(result).extracting(ReviewDTO::getAvatarUrl).containsExactly(userClient.getFile().getUrl());
        assertThat(result).extracting(ReviewDTO::getDescription).containsExactly(review.getDescription());
        assertThat(result).extracting(ReviewDTO::getRating).containsExactly(review.getRating());

        verify(this.repository).findAllByProductId(review.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void saveReview() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setFlavors(Collections.singletonList(productFlavorDTO));
        dto.setProductId(UUID.randomUUID());
        dto.setUserClientId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        Review review = dtoConverter.from(dto);

        Publication publication = new Publication(review);

        when(this.repository.existsByUserIdAndProductId(dto.getUserClientId(), dto.getProductId())).thenReturn(false);
        when(this.repository.saveAndFlush(review)).thenReturn(review);
        when(this.publicationRepository.save(publication)).thenReturn(publication);

        this.service.saveReview(dto);

        verify(this.repository).existsByUserIdAndProductId(dto.getUserClientId(), dto.getProductId());
        verify(this.repository).saveAndFlush(review);
        verify(this.publicationRepository).save(publication);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void saveReviewNoProductIdException() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setFlavors(Collections.singletonList(productFlavorDTO));
        dto.setUserClientId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        Exception exception = assertThrows(CreationNotPermittedException.class, () -> this.service.saveReview(dto));

        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível salvar o registro. Motivo: Registro sem produto vínculado. Atualize e tente novamente.");
    }

    @Test
    public void saveReviewNoUserIdException() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setFlavors(Collections.singletonList(productFlavorDTO));
        dto.setProductId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        Exception exception = assertThrows(CreationNotPermittedException.class, () -> this.service.saveReview(dto));

        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível salvar o registro. Motivo: Registro sem usuário vínculado. Atualize e tente novamente.");
    }

    @Test
    public void saveReviewNoFlavorsSelectedException() {

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setProductId(UUID.randomUUID());
        dto.setUserClientId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        Exception exception = assertThrows(CreationNotPermittedException.class, () -> this.service.saveReview(dto));

        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível salvar o registro. Motivo: Nenhum sabor foi selecionado.");
    }

    @Test
    public void saveReviewExistsRegisterWithSameUserIdAndProductIdException() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setFlavors(Collections.singletonList(productFlavorDTO));
        dto.setProductId(UUID.randomUUID());
        dto.setUserClientId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        when(this.repository.existsByUserIdAndProductId(dto.getUserClientId(), dto.getProductId())).thenReturn(true);

        Exception exception = assertThrows(CreationNotPermittedException.class, () -> this.service.saveReview(dto));

        assertThat(exception.getMessage())
                .isEqualTo("Não foi possível salvar o registro. Motivo: O usuário logado já fez uma avaliação para o produto selecionado.");

        verify(this.repository).existsByUserIdAndProductId(dto.getUserClientId(), dto.getProductId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void isProductReviewed() {

        UUID userId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(this.repository.existsByUserIdAndProductId(userId, productId)).thenReturn(true);

        Boolean result = this.service.isProductReviewed(userId, productId);

        assertThat(result).isTrue();

        verify(this.repository).existsByUserIdAndProductId(userId, productId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findById() {

        UserClient userClient = new UserClient();

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Thiago Bussola");

        userClient.setId(UUID.randomUUID());
        userClient.setPerson(person);

        Product product = new Product();
        product.setName("Skoll Test");

        UUID id = UUID.randomUUID();

        Review review = new Review();
        review.setUser(userClient);
        review.setProduct(product);

        when(this.repository.findById(id)).thenReturn(Optional.of(review));

        this.service.findById(id);

        verify(this.repository).findById(id);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findByIdException() {

        UUID id = UUID.randomUUID();

        when(this.repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> this.service.findById(id));

        assertThat(exception.getMessage())
                .isEqualTo("O registro não foi encontrado.");

        verify(this.repository).findById(id);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void saveReviewPicture() throws IOException {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "fileData",
                "teste.jpeg",
                "text/plain",
                "{\"key1\": \"value1\"}".getBytes());

        UUID id = UUID.randomUUID();

        Review review = new Review();
        review.setId(id);

        when(this.repository.findById(id)).thenReturn(Optional.of(review));
        when(this.fileService.createImage(mockMultipartFile, "reviews", id))
                .thenReturn(new File());
        when(this.repository.save(review)).thenReturn(review);

        this.service.saveReviewPicture(mockMultipartFile, id);

        verify(this.repository).findById(id);
        verify(this.fileService).createImage(mockMultipartFile, "reviews", id);
        verify(this.repository).save(review);
        verifyNoMoreInteractions(this.fileService, this.repository);
    }
}

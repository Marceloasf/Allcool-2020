package br.com.allcool.converter;

import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewProductFlavorDTO;
import br.com.allcool.file.domain.File;
import br.com.allcool.person.domain.Person;
import br.com.allcool.product.domain.Product;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.domain.ReviewProductFlavor;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewDTOConverterTest {

    private final ReviewDTOConverter dtoConverter = new ReviewDTOConverter();

    @Test
    public void to() {

        File file = new File();
        file.setUrl("www.teste.com.br");

        File userFile = new File();
        file.setUrl("www.testeReview.com.br");

        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setName("Claudinho");

        UserClient userClient = new UserClient();
        userClient.setPerson(person);
        userClient.setFile(userFile);

        Product product = new Product();
        product.setName("Brahma Extra");

        ReviewProductFlavor reviewProductFlavor = new ReviewProductFlavor();
        reviewProductFlavor.setId(UUID.randomUUID());

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setDescription("Uma cerveja muito boa!");
        review.setUser(userClient);
        review.setProduct(product);
        review.setFile(file);
        review.getFlavors().add(reviewProductFlavor);

        ReviewDTO dto = this.dtoConverter.to(review);

        assertThat(dto.getId()).isEqualTo(review.getId());
        assertThat(dto.getUserName()).isEqualTo(review.getUser().getPerson().getName());
        assertThat(dto.getProductName()).isEqualTo(review.getProduct().getName());
        assertThat(dto.getAvatarUrl()).isEqualTo(review.getUser().getFile().getUrl());
        assertThat(dto.getPictureUrl()).isEqualTo(review.getFile().getUrl());
        assertThat(dto.getDescription()).isEqualTo(review.getDescription());
        assertThat(dto.getRating()).isEqualTo(review.getRating());
        assertThat(dto.getCreationDate()).isEqualTo(review.getCreationDate());
        assertThat(dto.getFlavors()).extracting(ReviewProductFlavorDTO::getId)
                .containsExactly(reviewProductFlavor.getId());
    }
}

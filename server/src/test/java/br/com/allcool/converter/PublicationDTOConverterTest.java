package br.com.allcool.converter;

import br.com.allcool.dto.PublicationDTO;
import br.com.allcool.news.domain.News;
import br.com.allcool.person.domain.Person;
import br.com.allcool.product.domain.Product;
import br.com.allcool.publication.domain.Publication;
import br.com.allcool.review.domain.Review;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PublicationDTOConverterTest {

    private final PublicationDTOConverter dtoConverter = new PublicationDTOConverter();

    @Test
    public void toWithReview() {

        Product product = new Product();

        Person person = new Person();
        person.setName("Fulano");

        UserClient user = new UserClient();
        user.setPerson(person);

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setUser(user);
        review.setProduct(product);

        Publication publication = new Publication(review);
        publication.setId(UUID.randomUUID());

        PublicationDTO dto = this.dtoConverter.to(publication);

        assertThat(dto.getId()).isEqualTo(publication.getId());
        assertThat(dto.getReview().getId()).isEqualTo(publication.getReview().getId());
        assertThat(dto.getNews()).isNull();
        assertThat(dto.getType()).isEqualTo(publication.getType());
    }

    @Test
    public void toWithNews() {

        News news = new News();
        news.setId(UUID.randomUUID());

        Publication publication = new Publication(news);
        publication.setId(UUID.randomUUID());

        PublicationDTO dto = this.dtoConverter.to(publication);

        assertThat(dto.getId()).isEqualTo(publication.getId());
        assertThat(dto.getReview()).isNull();
        assertThat(dto.getNews().getId()).isEqualTo(publication.getNews().getId());
        assertThat(dto.getType()).isEqualTo(publication.getType());
    }
}
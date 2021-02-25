package br.com.allcool.review.repository;

import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.domain.ReviewProductFlavor;
import br.com.allcool.test.RepositoryTest;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/person/person.sql", "/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/user/userclient.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/product/productflavor.sql",
        "/sql/review/review.sql", "/sql/review/reviewproductflavor.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    private final UUID REVIEW_ID = UUID.fromString("d6353fa6-796e-4fca-aa11-d731633782dd");

    @Test
    public void findAll() {
        List<Review> reviewList = this.repository.findAll();

        assertThat(reviewList).hasSize(2);

        assertThat(reviewList).extracting(review -> review.getUser().getId())
                .containsExactly(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"),
                        UUID.fromString("7db7d491-8f37-4bd6-a061-e865f9df4b6a"));
        assertThat(reviewList).extracting(review -> review.getProduct().getId())
                .containsExactly(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(reviewList).extracting(review -> review.getFile().getId())
                .containsExactly(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"),
                        UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));
        assertThat(reviewList).extracting(Review::getDescription)
                .containsExactly("Review teste", "Review teste allcool");
        assertThat(reviewList).extracting(review -> review.getRating().toString())
                .containsExactly("3.00", "5.00");
        assertThat(reviewList).extracting(Review::getCreationDate).containsOnly(LocalDate.of(2020, 1, 1));
    }

    @Test
    public void delete() {
        List<Review> reviewListBeforeDelete = this.repository.findAll();

        assertThat(reviewListBeforeDelete).hasSize(2);

        this.repository.deleteById(REVIEW_ID);

        List<Review> reviewListAfterDelete = this.repository.findAll();

        assertThat(reviewListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        UserClient userClient = new UserClient();
        userClient.setId(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"));

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        File file = new File();
        file.setId(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        Review review = new Review();
        review.setUser(userClient);
        review.setProduct(product);
        review.setFile(file);
        review.setDescription("Review teste 1");
        review.setRating(BigDecimal.valueOf(3));

        ReviewProductFlavor reviewProductFlavor = new ReviewProductFlavor();
        reviewProductFlavor.setReview(review);
        reviewProductFlavor.setDescription("Teste descrição");
        reviewProductFlavor.setType(FlavorTypeEnum.SWEET);

        review.getFlavors().add(reviewProductFlavor);

        Review savedReview = this.repository.saveAndFlush(review);

        assertThat(savedReview.getId()).isNotNull();
        assertThat(savedReview.getUser()).isEqualTo(review.getUser());
        assertThat(savedReview.getProduct()).isEqualTo(review.getProduct());
        assertThat(savedReview.getFile()).isEqualTo(review.getFile());
        assertThat(savedReview.getDescription()).isEqualTo(review.getDescription());
        assertThat(savedReview.getRating()).isEqualTo(review.getRating());
    }

    @Test
    public void update() {

        Review reviewBeforeUpdate = this.repository.findById(REVIEW_ID).get();

        assertThat(reviewBeforeUpdate.getId()).isEqualTo(REVIEW_ID);
        assertThat(reviewBeforeUpdate.getDescription()).isEqualTo("Review teste");
        assertThat(reviewBeforeUpdate.getRating().toString()).isEqualTo("3.00");

        reviewBeforeUpdate.setDescription("Review teste allcool!");
        reviewBeforeUpdate.setRating(BigDecimal.valueOf(5));

        Review reviewAfterUpdate = this.repository.saveAndFlush(reviewBeforeUpdate);

        assertThat(reviewAfterUpdate.getId()).isEqualTo(REVIEW_ID);
        assertThat(reviewAfterUpdate.getDescription()).isEqualTo("Review teste allcool!");
        assertThat(reviewAfterUpdate.getRating()).isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    public void existsByUserIdAndProductId() {

        assertThat(this.repository.existsByUserIdAndProductId(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"),
                UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"))).isTrue();

        assertThat(this.repository.existsByUserIdAndProductId(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"),
                UUID.randomUUID())).isFalse();

        assertThat(this.repository.existsByUserIdAndProductId(UUID.randomUUID(),
                UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"))).isFalse();
    }

    @Test
    public void findAllByProductId() {

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        List<Review> listReview = this.repository
                .findAllByProductId(product.getId());

        assertThat(listReview).hasSize(1);
        assertThat(listReview).extracting(Review::getId)
                .containsExactly(UUID.fromString("d6353fa6-796e-4fca-aa11-d731633782dd"));
        assertThat(listReview).extracting(Review::getDescription)
                .containsExactly("Review teste");
        assertThat(listReview).extracting(Review::getRating).toString()
                .equals(BigDecimal.valueOf(3));

    }
}
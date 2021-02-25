package br.com.allcool.review.repository;

import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.domain.ReviewProductFlavor;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/person/person.sql", "/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/user/userclient.sql",
                "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/review/review.sql",
                "/sql/review/reviewproductflavor.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ReviewProductFlavorRepositoryTest {

    @Autowired
    private ReviewProductFlavorRepository repository;

    private final UUID REVIEWPRODUCTFLAVOR_ID = UUID.fromString("c45db5f6-a6ee-4b8e-b470-048f1c7becd1");

    @Test
    public void findAll() {
        List<ReviewProductFlavor> reviewProductFlavorList = this.repository.findAll();

        assertThat(reviewProductFlavorList).hasSize(2);
        assertThat(reviewProductFlavorList).extracting(reviewProductFlavor -> reviewProductFlavor.getReview().getId())
                .containsExactly(UUID.fromString("d6353fa6-796e-4fca-aa11-d731633782dd"),
                                 UUID.fromString("d8942a4c-183a-4261-83ff-6c466e5ced8f"));
        assertThat(reviewProductFlavorList).extracting(ReviewProductFlavor::getDescription)
                .containsExactly("Adocicado", "Umami");
        assertThat(reviewProductFlavorList).extracting(ReviewProductFlavor::getType)
                .containsExactly(FlavorTypeEnum.SWEET, FlavorTypeEnum.UMAMI);
    }

    @Test
    public void delete() {

        List<ReviewProductFlavor> reviewProductFlavorListBeforeDelete = this.repository.findAll();

        assertThat(reviewProductFlavorListBeforeDelete).hasSize(2);

        this.repository.deleteById(REVIEWPRODUCTFLAVOR_ID);

        List<ReviewProductFlavor> reviewProductFlavorListAfterDelete = this.repository.findAll();

        assertThat(reviewProductFlavorListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Review review = new Review();
        review.setId(UUID.fromString("d6353fa6-796e-4fca-aa11-d731633782dd"));

        ReviewProductFlavor reviewProductFlavor = new ReviewProductFlavor();
        reviewProductFlavor.setReview(review);
        reviewProductFlavor.setDescription("Product flavor");
        reviewProductFlavor.setType(FlavorTypeEnum.SWEET);


        ReviewProductFlavor savedReviewProductFlavor = this.repository.saveAndFlush(reviewProductFlavor);

        assertThat(savedReviewProductFlavor.getId()).isNotNull();
        assertThat(savedReviewProductFlavor.getReview()).isEqualTo(reviewProductFlavor.getReview());
        assertThat(savedReviewProductFlavor.getDescription()).isEqualTo(reviewProductFlavor.getDescription());
    }

    @Test
    public void update() {

        ReviewProductFlavor reviewProductFlavorBeforeUpdate = this.repository.findById(REVIEWPRODUCTFLAVOR_ID).get();

        assertThat(reviewProductFlavorBeforeUpdate.getId()).isEqualTo(REVIEWPRODUCTFLAVOR_ID);
        assertThat(reviewProductFlavorBeforeUpdate.getDescription()).isEqualTo("Adocicado");
        assertThat(reviewProductFlavorBeforeUpdate.getType()).isEqualByComparingTo(FlavorTypeEnum.SWEET);

        reviewProductFlavorBeforeUpdate.setDescription("Sabor Update");
        reviewProductFlavorBeforeUpdate.setType(FlavorTypeEnum.UMAMI);

        ReviewProductFlavor reviewProductFlavorAfterUpdate = this.repository.saveAndFlush(reviewProductFlavorBeforeUpdate);

        assertThat(reviewProductFlavorAfterUpdate.getId()).isEqualTo(REVIEWPRODUCTFLAVOR_ID);
        assertThat(reviewProductFlavorAfterUpdate.getDescription()).isEqualTo("Sabor Update");
        assertThat(reviewProductFlavorAfterUpdate.getType()).isEqualByComparingTo(FlavorTypeEnum.UMAMI);
    }
}

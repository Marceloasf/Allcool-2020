package br.com.allcool.converter;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.domain.ReviewProductFlavor;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewFormDTOConverterTest {

    @Test
    public void from() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewFormDTO dto = new ReviewFormDTO();
        dto.setDescription("Great beer");
        dto.setFlavors(Collections.singletonList(productFlavorDTO));
        dto.setProductId(UUID.randomUUID());
        dto.setUserClientId(UUID.randomUUID());
        dto.setRating(BigDecimal.valueOf(5));

        Review review = new ReviewFormDTOConverter().from(dto);

        assertThat(review.getDescription()).isEqualTo(dto.getDescription());
        assertThat(review.getRating()).isEqualTo(dto.getRating());
        assertThat(review.getProduct().getId()).isEqualTo(dto.getProductId());
        assertThat(review.getUser().getId()).isEqualTo(dto.getUserClientId());
        assertThat(review.getFlavors()).extracting(ReviewProductFlavor::getDescription)
                .containsExactly(dto.getFlavors().get(0).getDescription());
        assertThat(review.getFlavors()).extracting(ReviewProductFlavor::getType)
                .containsExactly(dto.getFlavors().get(0).getType());
    }
}
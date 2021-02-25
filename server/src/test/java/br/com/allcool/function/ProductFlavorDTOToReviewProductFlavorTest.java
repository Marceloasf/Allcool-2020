package br.com.allcool.function;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.review.domain.ReviewProductFlavor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFlavorDTOToReviewProductFlavorTest {

    @Test
    public void apply() {

        ProductFlavorDTO productFlavorDTO = new ProductFlavorDTO();
        productFlavorDTO.setDescription("Flavor 1");
        productFlavorDTO.setType(FlavorTypeEnum.SWEET);

        ReviewProductFlavor reviewProductFlavor = new ProductFlavorDTOToReviewProductFlavor().apply(productFlavorDTO);

        assertThat(reviewProductFlavor.getDescription()).isEqualTo("Flavor 1");
        assertThat(reviewProductFlavor.getType()).isEqualTo(FlavorTypeEnum.SWEET);
    }
}
package br.com.allcool.function;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.review.domain.ReviewProductFlavor;

import java.util.function.Function;


public class ProductFlavorDTOToReviewProductFlavor implements Function<ProductFlavorDTO, ReviewProductFlavor> {

    @Override
    public ReviewProductFlavor apply(ProductFlavorDTO productFlavorDTO) {

        ReviewProductFlavor reviewProductFlavor = new ReviewProductFlavor();
        reviewProductFlavor.setDescription(productFlavorDTO.getDescription());
        reviewProductFlavor.setType(productFlavorDTO.getType());

        return reviewProductFlavor;
    }
}

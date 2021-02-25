package br.com.allcool.converter;

import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.function.ProductFlavorDTOToReviewProductFlavor;
import br.com.allcool.product.domain.Product;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.domain.ReviewProductFlavor;
import br.com.allcool.user.domain.UserClient;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ReviewFormDTOConverter {

    private final ProductFlavorDTOToReviewProductFlavor productFlavorFunction = new ProductFlavorDTOToReviewProductFlavor();

    public Review from(ReviewFormDTO dto) {

        Review entity = new Review();

        if (isNull(dto)) {
            return entity;
        }

        Product product = new Product();
        product.setId(dto.getProductId());

        entity.setProduct(product);

        UserClient userClient = new UserClient();
        userClient.setId(dto.getUserClientId());

        entity.setUser(userClient);

        List<ReviewProductFlavor> flavors =
                dto.getFlavors().stream().map(productFlavorFunction).collect(Collectors.toList());

        flavors.forEach(f -> f.setReview(entity));

        entity.setFlavors(flavors);
        entity.setRating(dto.getRating());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}

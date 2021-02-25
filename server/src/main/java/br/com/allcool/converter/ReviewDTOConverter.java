package br.com.allcool.converter;

import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewProductFlavorDTO;
import br.com.allcool.review.domain.Review;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ReviewDTOConverter {

    public ReviewDTO to(Review review) {

        ReviewDTO dto = new ReviewDTO();

        if (isNull(review)) {
            return dto;
        }

        dto.setId(review.getId());
        dto.setUserName(review.getUser().getPerson().getName());
        dto.setProductName(review.getProduct().getName());
        dto.setCreationDate(review.getCreationDate());

        if (nonNull(review.getUser().getFile())) {
            dto.setAvatarUrl(review.getUser().getFile().getUrl());
        }

        if (nonNull(review.getFile())) {
            dto.setPictureUrl(review.getFile().getUrl());
        }

        dto.setDescription(review.getDescription());
        dto.setRating(review.getRating());
        dto.setFlavors(review.getFlavors().stream()
                .map(flavor -> new ReviewProductFlavorDTO(flavor.getId(), flavor.getType(), flavor.getDescription()))
                .collect(Collectors.toList()));

        return dto;
    }
}

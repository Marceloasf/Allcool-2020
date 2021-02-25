package br.com.allcool.converter;

import br.com.allcool.dto.PublicationDTO;
import br.com.allcool.publication.domain.Publication;

import static java.util.Objects.nonNull;

import java.util.Objects;

public class PublicationDTOConverter {

    private final ReviewDTOConverter reviewDTOConverter = new ReviewDTOConverter();
    private final NewsDTOConverter newsDTOConverter = new NewsDTOConverter();

    public PublicationDTO to(Publication publication) {
        PublicationDTO dto = new PublicationDTO();

        if (Objects.isNull(publication)) {
            return dto;
        }

        dto.setId(publication.getId());
        dto.setType(publication.getType());
        dto.setCreationDate(publication.getCreationDate());

        if (nonNull(publication.getReview())) {
            dto.setReview(reviewDTOConverter.to(publication.getReview()));
        }

        if (nonNull(publication.getNews())) {
            dto.setNews(newsDTOConverter.to(publication.getNews()));
        }

        return dto;
    }
}

package br.com.allcool.converter;

import br.com.allcool.dto.NewsDTO;
import br.com.allcool.news.domain.News;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NewsDTOConverter {

    public NewsDTO to(News entity) {
        NewsDTO dto = new NewsDTO();

        if (isNull(entity)) {
            return dto;
        }

        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setDescription(entity.getDescription());
        dto.setRating(entity.getRating());
        dto.setEventDate(entity.getEventDate());
        dto.setType(entity.getType());
        dto.setTitle(entity.getTitle());

        if (nonNull(entity.getFile())) {
            dto.setPictureUrl(entity.getFile().getUrl());
        }


        return dto;
    }
}

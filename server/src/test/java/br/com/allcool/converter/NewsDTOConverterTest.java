package br.com.allcool.converter;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.NewsDTO;
import br.com.allcool.enums.NewsTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.news.domain.News;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class NewsDTOConverterTest {

    private final NewsDTOConverter dtoConverter = new NewsDTOConverter();

    @Test
    public void to() {

        Address address = new Address();
        address.setId(UUID.randomUUID());

        File file = new File();
        file.setUrl("www.test.com");

        News news = new News();
        news.setId(UUID.randomUUID());
        news.setAddress(address);
        news.setFile(file);
        news.setDescription("New News");
        news.setRating(BigDecimal.ONE);
        news.setCreationDate(LocalDate.of(2020,1,1));
        news.setEventDate(LocalDateTime.of(2020,2,5,20,30));
        news.setType(NewsTypeEnum.EVENT);

        NewsDTO dto = dtoConverter.to(news);

        assertThat(dto.getId()).isEqualTo(news.getId());
        assertThat(dto.getAddress().getId()).isEqualTo(address.getId());
        assertThat(dto.getDescription()).isEqualTo(news.getDescription());
        assertThat(dto.getEventDate()).isEqualTo(news.getEventDate());
        assertThat(dto.getRating()).isEqualTo(news.getRating());
        assertThat(dto.getType()).isEqualTo(news.getType());
        assertThat(dto.getPictureUrl()).isEqualTo(news.getFile().getUrl());
    }
}
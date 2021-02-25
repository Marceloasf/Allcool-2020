package br.com.allcool.dto;

import br.com.allcool.address.domain.Address;
import br.com.allcool.enums.NewsTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NewsDTO {

    private UUID id;
    private Address address;
    private String title;
    private String description;
    private BigDecimal rating;
    private String pictureUrl;
    private LocalDateTime eventDate;
    private NewsTypeEnum type;
}

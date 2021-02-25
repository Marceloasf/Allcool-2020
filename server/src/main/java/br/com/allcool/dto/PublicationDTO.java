package br.com.allcool.dto;

import br.com.allcool.enums.PublicationTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PublicationDTO {

    private UUID id;
    private ReviewDTO review;
    private NewsDTO news;
    private PublicationTypeEnum type;
    private LocalDate creationDate;
}

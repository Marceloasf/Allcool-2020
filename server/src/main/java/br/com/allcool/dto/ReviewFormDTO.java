package br.com.allcool.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ReviewFormDTO {

    private UUID id;
    private UUID userClientId;
    private UUID productId;
    private String description;
    private BigDecimal rating;
    private List<ProductFlavorDTO> flavors = new ArrayList<>();
}

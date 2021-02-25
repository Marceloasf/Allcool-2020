package br.com.allcool.dto;

import br.com.allcool.enums.FlavorTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFlavorDTO {

    private UUID id;
    private String description;
    private FlavorTypeEnum type;
}

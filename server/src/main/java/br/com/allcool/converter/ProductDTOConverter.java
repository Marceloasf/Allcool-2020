package br.com.allcool.converter;

import java.util.Objects;

import br.com.allcool.dto.ProductDTO;
import br.com.allcool.product.domain.Product;

public class ProductDTOConverter {

    public ProductDTO to(Product product) {
        ProductDTO dto = new ProductDTO();

        if (Objects.isNull(product)) {
            return dto;
        }

        dto.setId(product.getId());
        dto.setType(product.getType().getDescription());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand().getName());

        return dto;
    }
}

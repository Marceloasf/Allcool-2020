package br.com.allcool.converter;

import br.com.allcool.brand.domain.Brand;
import br.com.allcool.dto.ProductDTO;
import br.com.allcool.product.domain.Product;
import br.com.allcool.producttype.domain.ProductType;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDTOConverterTest {

    private final ProductDTOConverter dtoConverter = new ProductDTOConverter();

    @Test
    public void to() {

        Brand brand = new Brand();
        brand.setName("Skol");

        ProductType productType = new ProductType();
        productType.setDescription("American Lager");

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setBrand(brand);
        product.setName("Skol Pilsen");
        product.setType(productType);

        ProductDTO dto = this.dtoConverter.to(product);

        assertThat(dto.getId()).isEqualTo(product.getId());
        assertThat(dto.getBrand()).isEqualTo(brand.getName());
        assertThat(dto.getName()).isEqualTo(product.getName());
        assertThat(dto.getType()).isEqualTo(productType.getDescription());
    }
}
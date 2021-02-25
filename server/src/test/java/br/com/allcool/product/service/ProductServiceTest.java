package br.com.allcool.product.service;

import br.com.allcool.brand.domain.Brand;
import br.com.allcool.dto.ProductDTO;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductFile;
import br.com.allcool.product.repository.ProductFileRepository;
import br.com.allcool.product.repository.ProductRepository;
import br.com.allcool.producttype.domain.ProductType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductFileRepository productFileRepository;

    @Test
    public void findById(){

        UUID id = UUID.randomUUID();

        when(this.repository.findById(id)).thenReturn(Optional.of(new Product()));
        when(this.repository.getProductAverageRating(id)).thenReturn(BigDecimal.valueOf(3.5));

        this.service.findById(id);

        verify(this.repository).findById(id);
        verify(this.repository).getProductAverageRating(id);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findAll() {

        File file = new File();
        file.setUrl("teste.com");

        ProductFile productFile = new ProductFile();
        productFile.setFile(file);

        Brand brand = new Brand();
        brand.setName("Skol");

        ProductType productType = new ProductType();
        productType.setDescription("American Lager");

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setBrand(brand);
        product.setName("Skol Pilsen");
        product.setType(productType);

        when(this.repository.findAll()).thenReturn(Collections.singletonList(product));
        when(this.productFileRepository.findOneByProductIdAndListedTrue(product.getId())).thenReturn(productFile);

        List<ProductDTO> result = this.service.findAll();

        assertThat(result).extracting(ProductDTO::getId).containsExactly(product.getId());
        assertThat(result).extracting(ProductDTO::getBrand).containsExactly(brand.getName());
        assertThat(result).extracting(ProductDTO::getName).containsExactly(product.getName());
        assertThat(result).extracting(ProductDTO::getType).containsExactly(productType.getDescription());
        assertThat(result).extracting(ProductDTO::getImageUrl).containsExactly(file.getUrl());

        verify(this.repository).findAll();
        verify(this.productFileRepository).findOneByProductIdAndListedTrue(product.getId());
        verifyNoMoreInteractions(this.repository);
    }
}

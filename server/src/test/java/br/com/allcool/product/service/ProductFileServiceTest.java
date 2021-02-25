package br.com.allcool.product.service;

import br.com.allcool.dto.ProductFileDTO;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductFile;
import br.com.allcool.product.repository.ProductFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductFileServiceTest {

    @InjectMocks
    private ProductFileService service;

    @Mock
    private ProductFileRepository productFileRepository;

    @Test
    public void findAllByProductId() {

        Product product = new Product();
        product.setId(UUID.randomUUID());

        File file = new File();
        file.setUrl("www.teste.com");

        ProductFile productFile = new ProductFile();
        productFile.setId(UUID.randomUUID());
        productFile.setFile(file);
        productFile.setProduct(product);
        productFile.setListed(true);

        when(this.productFileRepository.findAllByProductId(product.getId())).thenReturn(Collections.singletonList(productFile));

        List<ProductFileDTO> result = this.service.findAllByProductId(product.getId());

        assertThat(result).extracting(ProductFileDTO::getId).containsExactly(productFile.getId());
        assertThat(result).extracting(ProductFileDTO::getUrl).containsExactly(file.getUrl());

        verify(this.productFileRepository).findAllByProductId(product.getId());
        verifyNoMoreInteractions(this.productFileRepository);
    }
}

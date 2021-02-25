package br.com.allcool.product.service;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.product.domain.ProductFlavor;
import br.com.allcool.product.repository.ProductFlavorRepository;
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
public class ProductFlavorServiceTest {

    @InjectMocks
    private ProductFlavorService service;

    @Mock
    private ProductFlavorRepository repository;

    @Test
    public void findAllByProductId() {

        UUID productId = UUID.randomUUID();

        ProductFlavor productFlavor = new ProductFlavor();
        productFlavor.setId(UUID.randomUUID());
        productFlavor.setDescription("Amargo");
        productFlavor.setType(FlavorTypeEnum.BITTER);

        when(this.repository.findAllByProductId(productId)).thenReturn(Collections.singletonList(productFlavor));

        List<ProductFlavorDTO> dtoList = this.service.findAllByProductId(productId);

        assertThat(dtoList).extracting(ProductFlavorDTO::getId).containsExactly(productFlavor.getId());
        assertThat(dtoList).extracting(ProductFlavorDTO::getDescription).containsExactly(productFlavor.getDescription());

        verify(this.repository).findAllByProductId(productId);
        verifyNoMoreInteractions(this.repository);
    }
}
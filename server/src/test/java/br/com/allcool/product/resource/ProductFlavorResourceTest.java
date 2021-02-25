package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.product.service.ProductFlavorService;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(ProductFlavorResource.class)
public class ProductFlavorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductFlavorService service;

    @Test
    public void findAllByProductId() throws Exception {

        UUID productId = UUID.randomUUID();

        ProductFlavorDTO dto = new ProductFlavorDTO();
        dto.setId(UUID.randomUUID());
        dto.setDescription("Amargo");

        when(this.service.findAllByProductId(productId)).thenReturn(Collections.singletonList(dto));

        this.mockMvc.perform(get("/api/products-flavors/{id}", productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(dto.getId().toString())))
                .andExpect(jsonPath("$.[*].description", hasItems(dto.getDescription())));

        verify(this.service).findAllByProductId(productId);
        verifyNoMoreInteractions(this.service);
    }
}
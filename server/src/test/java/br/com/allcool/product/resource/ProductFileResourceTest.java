package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductFileDTO;
import br.com.allcool.product.service.ProductFileService;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(ProductFileResource.class)
public class ProductFileResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductFileService service;

    private final UUID ADDRESS_ID = UUID.fromString("f50022f-4058-4f8e-8062-fc0ef9bc327e");

    @Test
    public void findAllByProductId() throws Exception {

        ProductFileDTO productFile1DTO = new ProductFileDTO();
        productFile1DTO.setId(UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));
        productFile1DTO.setUrl("www.teste.com.br");

        ProductFileDTO productFile2DTO = new ProductFileDTO();
        productFile2DTO.setId(UUID.fromString("757daf1c-ad4f-4bac-8e0e-b4594d05fb6c"));
        productFile2DTO.setUrl("www.teste123.com.br");

        List<ProductFileDTO> listProductFileDTOS = new ArrayList<>();
        listProductFileDTOS.add(productFile1DTO);
        listProductFileDTOS.add(productFile2DTO);

        when(this.service.findAllByProductId(ADDRESS_ID)).thenReturn(listProductFileDTOS);

        this.mockMvc.perform(get("/api/products-files/{productId}", ADDRESS_ID))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(productFile1DTO.getId().toString(),
                        productFile2DTO.getId().toString())))
                .andExpect(jsonPath("$.[*].url", hasItems(productFile1DTO.getUrl(),
                        productFile2DTO.getUrl())));

        verify(this.service).findAllByProductId(ADDRESS_ID);
        verifyNoMoreInteractions(this.service);
    }
}

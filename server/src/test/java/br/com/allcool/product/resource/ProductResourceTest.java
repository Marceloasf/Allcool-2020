package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductDTO;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductFlavor;
import br.com.allcool.product.service.ProductService;
import br.com.allcool.test.ResourceTest;
import org.hamcrest.CoreMatchers;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(ProductResource.class)
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void findById() throws Exception {

        ProductFlavor flavor1 = new ProductFlavor();
        flavor1.setId(UUID.randomUUID());

        ProductFlavor flavor2 = new ProductFlavor();
        flavor2.setId(UUID.randomUUID());

        ProductFlavor flavor3 = new ProductFlavor();
        flavor3.setId(UUID.randomUUID());

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Product Name Test");
        product.setDescription("Product Description Test");
        product.setCode(1L);
        product.setActive(Boolean.TRUE);
        product.getFlavors().add(flavor1);
        product.getFlavors().add(flavor2);
        product.getFlavors().add(flavor3);

        when(this.service.findById(product.getId())).thenReturn(product);

        this.mockMvc.perform(get("/api/products/{id}", product.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(product.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.description", equalTo(product.getDescription())))
                .andExpect(jsonPath("$.code", equalTo(product.getCode().intValue())))
                .andExpect(jsonPath("$.active", equalTo(product.getActive())))
                .andExpect(jsonPath("$.flavors[*].id",
                        CoreMatchers.hasItems(flavor1.getId().toString(),
                                flavor2.getId().toString(), flavor3.getId().toString())));

        verify(this.service).findById(product.getId());
        verifyNoMoreInteractions(this.service);
    }

    @Test
    public void findAll() throws Exception {

        ProductDTO productBeerTest = new ProductDTO();
        productBeerTest.setId(UUID.randomUUID());
        productBeerTest.setName("Product Beer Test");
        productBeerTest.setType("Beer test");
        productBeerTest.setImageUrl("ABC.jpg");
        productBeerTest.setBrand("Brand A");

        ProductDTO productSodaTest = new ProductDTO();
        productSodaTest.setId(UUID.randomUUID());
        productSodaTest.setName("Product Soda Test");
        productSodaTest.setType("Soda Test");
        productSodaTest.setImageUrl("DFG.png");
        productSodaTest.setBrand("Brand B");

        List<ProductDTO> productList = new ArrayList<>();
        productList.add(productBeerTest);
        productList.add(productSodaTest);

        when(this.service.findAll()).thenReturn(productList);

        this.mockMvc.perform(get("/api/products"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(productBeerTest.getId().toString(),
                        productSodaTest.getId().toString())))
                .andExpect(jsonPath("$.[*].type", hasItems(productBeerTest.getType(),
                        productSodaTest.getType())))
                .andExpect(jsonPath("$.[*].imageUrl", hasItems(productBeerTest.getImageUrl(),
                        productSodaTest.getImageUrl())))
                .andExpect(jsonPath("$.[*].brand", hasItems(productBeerTest.getBrand(),
                        productSodaTest.getBrand())))
                .andExpect(jsonPath("$.[*].name", hasItems(productBeerTest.getName(),
                        productSodaTest.getName())));

        verify(this.service).findAll();
        verifyNoMoreInteractions(this.service);
    }
}

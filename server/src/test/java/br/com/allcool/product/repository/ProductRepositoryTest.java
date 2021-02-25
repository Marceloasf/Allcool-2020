package br.com.allcool.product.repository;

import br.com.allcool.brand.domain.Brand;
import br.com.allcool.container.domain.Container;
import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductContainer;
import br.com.allcool.product.domain.ProductFlavor;
import br.com.allcool.producttype.domain.ProductType;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/container/container.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql",
        "/sql/product/productflavor.sql", "/sql/product/productcontainer.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ProductRepositoryTest {

    private final UUID PRODUCT_ID = UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa");

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductContainerRepository productContainerRepository;

    @Test
    public void findAll() {

        List<Product> productList = this.repository.findAll();

        assertThat(productList).hasSize(2);
        assertThat(productList).extracting(Product::getName).containsExactlyInAnyOrder("Brahma Extra Red Lager", "Patagonia Amber Lager");
    }

    @Test
    public void save() {

        Brand brand = new Brand();
        brand.setId(UUID.fromString("c86c2e22-c34b-4c0c-aded-055d0c0e696a"));

        Container container = new Container();
        container.setId(UUID.fromString("f5014a75-c3db-40b8-a49b-2076e1b19801"));

        ProductContainer productContainer = new ProductContainer();
        productContainer.setId(UUID.fromString("6a8fee85-514a-4436-95a9-dd4961deb859"));
        productContainer.setContainer(container);

        ProductType producttype = new ProductType();
        producttype.setId(UUID.fromString("d6a0ed3a-82b7-4c10-9190-27a737faf454"));

        ProductFlavor productFlavor = new ProductFlavor();
        productFlavor.setId(UUID.fromString("f329cccf-c105-48f6-95c2-78a4ce11cf7f"));
        productFlavor.setDescription("Flavor Test");
        productFlavor.setType(FlavorTypeEnum.SALTY);

        Product product = new Product();
        product.setId(UUID.fromString("c76b4a9a-6033-4106-a847-4fa4c922bed8"));
        product.setName("Patagonia Bohemian Pilsener");
        product.getContainers().add(productContainer);
        product.getFlavors().add(productFlavor);
        product.setType(producttype);
        product.setActive(Boolean.TRUE);
        product.setHarmonization("Lula frita e peixes de sabor forte.");
        product.setCode(3L);
        product.setDescription("Tradicional cerveja do tipo Pilsen. Ela é produzida com o lúpulo tcheco Saaz que proporciona um aroma fresco e frutado bem característico, além de uma coloração dourada profunda.");
        product.setBrand(brand);

        productContainer.setProduct(product);
        productFlavor.setProduct(product);

        Product savedProduct = this.repository.saveAndFlush(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getCode()).isEqualTo(product.getCode());
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(savedProduct.getHarmonization()).isEqualTo(product.getHarmonization());
        assertThat(savedProduct.getActive()).isEqualTo(product.getActive());
        assertThat(savedProduct.getType()).isEqualTo(product.getType());
        assertThat(savedProduct.getContainers()).extracting(ProductContainer::getId).isNotNull();
        assertThat(savedProduct.getFlavors()).extracting(ProductFlavor::getId).isNotNull();
        assertThat(savedProduct.getBrand()).isEqualTo(brand);

    }

    @Test
    public void update() {

        Product productBeforeUpdate = this.repository.findById(PRODUCT_ID).get();

        assertThat(productBeforeUpdate.getId()).isEqualTo(PRODUCT_ID);
        assertThat(productBeforeUpdate.getCode()).isEqualTo(1L);
        assertThat(productBeforeUpdate.getName()).isEqualTo("Brahma Extra Red Lager");
        assertThat(productBeforeUpdate.getDescription()).isEqualTo("Sabor marcante que combina com massas e queijos é com a Brahma Red Lager. A cerveja tem um leve aroma de caramelo, malte torrado e suaves notas de frutas e doces.");
        assertThat(productBeforeUpdate.getHarmonization()).isEqualTo("Massas a Bolonhesa e Queijo Parmesão.");
        assertThat(productBeforeUpdate.getActive()).isEqualTo(true);
        assertThat(productBeforeUpdate.getType().getId()).isNotNull();

        productBeforeUpdate.setCode(5L);
        productBeforeUpdate.setName("Colorado Eugênia");
        productBeforeUpdate.setDescription("Eugênia é uma cerveja do estilo Session IPA, com aromas marcantes dos lúpulos americanos, alemães e franceses, com um ingrediente especial: a fruta Uvaia, típica da Mata Atlântica, rica em vitamina C e A, super aromática e com sabor cítrico. A Cerveja Colorado Eugênia é leve, refrescante e amarga na medida (40 IBU). Desiberne, cerveja pode ter fruta!");
        productBeforeUpdate.setHarmonization("Ceviche, comida mexicana (tacos, quesadilla), chicken wings, petiscos, churrasco e sobremesas ácidas.");
        productBeforeUpdate.setActive(false);

        Product productAfterUpdate = this.repository.saveAndFlush(productBeforeUpdate);

        assertThat(productAfterUpdate.getId()).isEqualTo(PRODUCT_ID);
        assertThat(productAfterUpdate.getCode()).isEqualTo(5L);
        assertThat(productAfterUpdate.getName()).isEqualTo("Colorado Eugênia");
        assertThat(productAfterUpdate.getDescription()).isEqualTo("Eugênia é uma cerveja do estilo Session IPA, com aromas marcantes dos lúpulos americanos, alemães e franceses, com um ingrediente especial: a fruta Uvaia, típica da Mata Atlântica, rica em vitamina C e A, super aromática e com sabor cítrico. A Cerveja Colorado Eugênia é leve, refrescante e amarga na medida (40 IBU). Desiberne, cerveja pode ter fruta!");
        assertThat(productAfterUpdate.getHarmonization()).isEqualTo("Ceviche, comida mexicana (tacos, quesadilla), chicken wings, petiscos, churrasco e sobremesas ácidas.");
        assertThat(productAfterUpdate.getActive()).isEqualTo(false);
    }

    @Test
    @Sql(scripts = {"/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/container/container.sql",
            "/sql/producttype/producttype.sql", "/sql/product/product.sql",
            "/sql/product/productflavor.sql", "/sql/product/productcontainer.sql",
            "/sql/person/person.sql", "/sql/user/userclient.sql", "/sql/review/review.sql"})
    public void getProductAverageRating() {

        assertThat(this.repository.getProductAverageRating(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa")))
                .isEqualTo(BigDecimal.valueOf(3.0));

        assertThat(this.repository.getProductAverageRating(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e")))
                .isEqualTo(BigDecimal.valueOf(5.0));
    }
}
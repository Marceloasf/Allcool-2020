package br.com.allcool.product.repository;

import br.com.allcool.enums.FlavorTypeEnum;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductFlavor;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/producttype/producttype.sql",
        "/sql/product/product.sql", "/sql/product/productflavor.sql"})
public class ProductFlavorRepositoryTest {

    @Autowired
    private ProductFlavorRepository repository;

    private final UUID PRODUCTFLAVOR_ID = UUID.fromString("f329cccf-c105-48f6-95c2-78a4ce11cf7f");

    @Test
    public void findAll() {

        List<ProductFlavor> productFlavorList = this.repository.findAll();

        assertThat(productFlavorList).hasSize(2);
        assertThat(productFlavorList).extracting(ProductFlavor::getType)
                .containsExactlyInAnyOrder(FlavorTypeEnum.SWEET, FlavorTypeEnum.BITTER);
        assertThat(productFlavorList).extracting(ProductFlavor::getDescription)
                .containsExactlyInAnyOrder("Amargo",
                        "Adocicado");
        assertThat(productFlavorList).extracting(productFlavor -> productFlavor.getProduct().getId())
                .containsExactlyInAnyOrder(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
    }

    @Test
    public void delete() {

        List<ProductFlavor> productFlavorList = this.repository.findAll();

        assertThat(productFlavorList).hasSize(2);

        this.repository.deleteById(PRODUCTFLAVOR_ID);

        List<ProductFlavor> productFlavorListAfterDelete = this.repository.findAll();

        assertThat(productFlavorListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        product.setName("Produto");

        ProductFlavor productFlavor = new ProductFlavor();
        productFlavor.setDescription("Descrição ex");
        productFlavor.setProduct(product);
        productFlavor.setType(FlavorTypeEnum.SALTY);

        ProductFlavor savedProductFlavor = this.repository.saveAndFlush(productFlavor);

        assertThat(savedProductFlavor.getId()).isNotNull();
        assertThat(savedProductFlavor.getDescription()).isEqualTo(productFlavor.getDescription());
        assertThat(savedProductFlavor.getProduct().getName()).isEqualTo(product.getName());
        assertThat(savedProductFlavor.getType()).isEqualByComparingTo(productFlavor.getType());
    }

    @Test
    public void update() {

        Product product = new Product();
        product.setId(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        ProductFlavor productFlavorBeforeUpdate = this.repository.findById(PRODUCTFLAVOR_ID).get();

        assertThat(productFlavorBeforeUpdate.getId()).isEqualTo(PRODUCTFLAVOR_ID);
        assertThat(productFlavorBeforeUpdate.getDescription())
                .isEqualTo("Adocicado");
        assertThat(productFlavorBeforeUpdate.getProduct().getId())
                .isEqualByComparingTo(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        assertThat(productFlavorBeforeUpdate.getType()).isEqualByComparingTo(FlavorTypeEnum.SWEET);

        productFlavorBeforeUpdate.setDescription("Teste Update");
        productFlavorBeforeUpdate.setProduct(product);
        productFlavorBeforeUpdate.setType(FlavorTypeEnum.SALTY);

        ProductFlavor productFileAfterUpdate = this.repository.saveAndFlush(productFlavorBeforeUpdate);

        assertThat(productFileAfterUpdate.getId()).isEqualTo(PRODUCTFLAVOR_ID);
        assertThat(productFileAfterUpdate.getDescription()).isEqualTo("Teste Update");
        assertThat(productFileAfterUpdate.getProduct().getId())
                .isEqualByComparingTo(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(productFileAfterUpdate.getType()).isEqualByComparingTo(FlavorTypeEnum.SALTY);
    }

    @Test
    public void findAllByProductId() {

        List<ProductFlavor> productFlavorList = this.repository.findAllByProductId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        assertThat(productFlavorList).hasSize(1);
        assertThat(productFlavorList).extracting(ProductFlavor::getDescription).containsExactly("Adocicado");
        assertThat(productFlavorList).extracting(ProductFlavor::getType).containsExactly(FlavorTypeEnum.SWEET);
    }
}

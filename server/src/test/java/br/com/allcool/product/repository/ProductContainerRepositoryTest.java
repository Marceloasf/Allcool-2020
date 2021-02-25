package br.com.allcool.product.repository;

import br.com.allcool.container.domain.Container;
import br.com.allcool.enums.ContainerTypeEnum;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductContainer;
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
@Sql(scripts = {"/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/container/container.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/product/productcontainer.sql"})
public class ProductContainerRepositoryTest {

    @Autowired
    private ProductContainerRepository repository;

    private final UUID PRODUCTCONTAINER_ID = UUID.fromString("6a8fee85-514a-4436-95a9-dd4961deb859");

    @Test
    public void findAll() {

        List<ProductContainer> productContainerList = this.repository.findAll();

        assertThat(productContainerList).hasSize(2);
        assertThat(productContainerList).extracting(productContainer -> productContainer.getProduct().getId()).containsExactlyInAnyOrder(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"), UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(productContainerList).extracting(productContainer -> productContainer.getContainer().getId()).containsExactlyInAnyOrder(UUID.fromString("f5014a75-c3db-40b8-a49b-2076e1b19801"), UUID.fromString("6e64b574-d43c-4278-96c6-ca772da91fbe"));
    }

    @Test
    public void delete() {

        List<ProductContainer> productContainerListBeforeDelete = this.repository.findAll();

        assertThat(productContainerListBeforeDelete).hasSize(2);

        this.repository.deleteById(PRODUCTCONTAINER_ID);

        List<ProductContainer> productContainerListAfterDelete = this.repository.findAll();

        assertThat(productContainerListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        product.setName("Produto");

        Container container = new Container();
        container.setId(UUID.fromString("6e64b574-d43c-4278-96c6-ca772da91fbe"));
        container.setType(ContainerTypeEnum.GLASSBOTTLE);

        ProductContainer productContainer = new ProductContainer();
        productContainer.setProduct(product);
        productContainer.setContainer(container);

        ProductContainer savedProductContainer = this.repository.saveAndFlush(productContainer);

        assertThat(savedProductContainer.getId()).isNotNull();
        assertThat(savedProductContainer.getProduct().getName()).isEqualTo(product.getName());
        assertThat(savedProductContainer.getContainer().getType()).isEqualTo(container.getType());

    }

    @Test
    public void update() {

        Product product = new Product();
        product.setId(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        Container container = new Container();
        container.setId(UUID.fromString("6e64b574-d43c-4278-96c6-ca772da91fbe"));

        ProductContainer productContainerBeforeUpdate = this.repository.findById(PRODUCTCONTAINER_ID).get();

        assertThat(productContainerBeforeUpdate.getId()).isEqualTo(PRODUCTCONTAINER_ID);
        assertThat(productContainerBeforeUpdate.getProduct().getId()).isEqualByComparingTo(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        assertThat(productContainerBeforeUpdate.getContainer().getId()).isEqualByComparingTo(UUID.fromString("f5014a75-c3db-40b8-a49b-2076e1b19801"));

        productContainerBeforeUpdate.setProduct(product);
        productContainerBeforeUpdate.setContainer(container);

        ProductContainer productContainerAfterUpdate = this.repository.saveAndFlush(productContainerBeforeUpdate);

        assertThat(productContainerAfterUpdate.getId()).isEqualTo(PRODUCTCONTAINER_ID);
        assertThat(productContainerAfterUpdate.getProduct().getId()).isEqualByComparingTo(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(productContainerAfterUpdate.getContainer().getId()).isEqualByComparingTo(UUID.fromString("6e64b574-d43c-4278-96c6-ca772da91fbe"));

    }

}

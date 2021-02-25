package br.com.allcool.producttype.repository;

import br.com.allcool.producttype.domain.ProductType;
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
@Sql(scripts = {"/sql/producttype/producttype.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ProductTypeRepositoryTest {

    @Autowired
    private ProductTypeRepository repository;

    private final UUID PRODUCTTYPE_ID = UUID.fromString("256b6606-97b6-11ea-bb37-0242ac130002");

    @Test
    public void findAll() {

        List<ProductType> addressList = this.repository.findAll();

        assertThat(addressList).hasSize(2);
        assertThat(addressList).extracting(ProductType::getCode).containsExactly(9565428L, 8784245L);
        assertThat(addressList).extracting(ProductType::getDescription).containsExactly("Cerveja", "Água");
    }

    @Test
    public void delete() {

        List<ProductType> productTypeListBeforeDelete = this.repository.findAll();

        assertThat(productTypeListBeforeDelete).hasSize(2);

        this.repository.deleteById(PRODUCTTYPE_ID);

        List<ProductType> productTypeListAfterDelete = this.repository.findAll();

        assertThat(productTypeListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        ProductType productType = new ProductType();
        productType.setCode(12352L);
        productType.setDescription("Refrigerante");


        ProductType savedProductType = this.repository.saveAndFlush(productType);

        assertThat(savedProductType.getId()).isNotNull();
        assertThat(savedProductType.getCode()).isEqualTo(productType.getCode());
        assertThat(savedProductType.getDescription()).isEqualTo(productType.getDescription());

    }

    @Test
    public void update() {

        ProductType productTypeBeforeUpdate = this.repository.findById(PRODUCTTYPE_ID).get();

        assertThat(productTypeBeforeUpdate.getId()).isEqualTo(PRODUCTTYPE_ID);
        assertThat(productTypeBeforeUpdate.getCode()).isEqualTo(8784245L);
        assertThat(productTypeBeforeUpdate.getDescription()).isEqualTo("Água");


        productTypeBeforeUpdate.setCode(123456L);
        productTypeBeforeUpdate.setDescription("Cerveja TOP");


        ProductType productTypeAfterUpdate = this.repository.saveAndFlush(productTypeBeforeUpdate);

        assertThat(productTypeAfterUpdate.getId()).isEqualTo(PRODUCTTYPE_ID);
        assertThat(productTypeAfterUpdate.getCode()).isEqualTo(123456L);
        assertThat(productTypeAfterUpdate.getDescription()).isEqualTo("Cerveja TOP");
    }
}

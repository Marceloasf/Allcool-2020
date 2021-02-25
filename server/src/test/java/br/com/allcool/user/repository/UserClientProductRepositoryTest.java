package br.com.allcool.user.repository;

import br.com.allcool.product.domain.Product;
import br.com.allcool.test.RepositoryTest;
import br.com.allcool.user.domain.UserClient;
import br.com.allcool.user.domain.UserClientProduct;
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
@Sql(scripts = {"/sql/file/file.sql", "/sql/producttype/producttype.sql", "/sql/brand/brand.sql",
        "/sql/product/product.sql", "/sql/person/person.sql", "/sql/user/userclient.sql",
        "/sql/user/userclient-product.sql"
})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class UserClientProductRepositoryTest {

    @Autowired
    private UserClientProductRepository repository;

    private final UUID USERPRODUCT_ID = UUID.fromString("62b01cba-6aa0-48dc-bfa5-f750a81ed667");

    @Test
    public void findById() {

        UserClientProduct userProduct = this.repository.findById(USERPRODUCT_ID).get();

        assertThat(userProduct.getProduct().getId()).isEqualTo(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        assertThat(userProduct.getUser().getId()).isEqualTo(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"));
    }

    @Test
    public void findAll() {

        List<UserClientProduct> userProduct = this.repository.findAll();

        assertThat(userProduct).hasSize(2);
        assertThat(userProduct).extracting(up -> up.getProduct().getId())
                .containsExactly(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(userProduct).extracting(up -> up.getUser().getId())
                .containsExactly(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"),
                        UUID.fromString("7db7d491-8f37-4bd6-a061-e865f9df4b6a"));
    }

    @Test
    public void delete() {

        List<UserClientProduct> userProductListBeforeDelete = this.repository.findAll();

        assertThat(userProductListBeforeDelete).hasSize(2);

        this.repository.deleteById(USERPRODUCT_ID);

        List<UserClientProduct> userProductListAfterDelete = this.repository.findAll();

        assertThat(userProductListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        UserClient userClient = new UserClient();
        userClient.setId(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"));

        UserClientProduct userProduct = new UserClientProduct();
        userProduct.setProduct(product);
        userProduct.setUser(userClient);

        UserClientProduct savedUserClientProduct = this.repository.saveAndFlush(userProduct);

        assertThat(savedUserClientProduct.getId()).isNotNull();
    }
}
package br.com.allcool.product.repository;

import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.domain.ProductFile;
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
        "/sql/product/product.sql", "/sql/product/productfile.sql"})
public class ProductFileRepositoryTest {

    @Autowired
    private ProductFileRepository repository;

    private final UUID PRODUCTFILE_ID = UUID.fromString("646cca9f-e90a-416a-a692-ae112968449e");

    @Test
    public void findAll() {

        List<ProductFile> productFileList = this.repository.findAll();

        assertThat(productFileList).hasSize(3);
        assertThat(productFileList).extracting(productFile -> productFile.getFile().getId())
                .containsExactlyInAnyOrder(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"),
                        UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"),
                        UUID.fromString("757daf1c-ad4f-4bac-8e0e-b4594d05fb6c"));
        assertThat(productFileList).extracting(productFile -> productFile.getProduct().getId())
                .containsExactlyInAnyOrder(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
    }

    @Test
    public void delete() {

        List<ProductFile> productFileListBeforeDelete = this.repository.findAll();

        assertThat(productFileListBeforeDelete).hasSize(3);

        this.repository.deleteById(PRODUCTFILE_ID);

        List<ProductFile> productFileListAfterDelete = this.repository.findAll();

        assertThat(productFileListAfterDelete).hasSize(2);
    }

    @Test
    public void save() {

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        product.setName("Produto");

        File file = new File();
        file.setId(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));
        file.setName("Test File");

        ProductFile productFile = new ProductFile();
        productFile.setProduct(product);
        productFile.setFile(file);

        ProductFile savedProductFile = this.repository.saveAndFlush(productFile);

        assertThat(savedProductFile.getId()).isNotNull();
        assertThat(savedProductFile.getProduct().getName()).isEqualTo(product.getName());
        assertThat(savedProductFile.getFile().getName()).isEqualTo(file.getName());

    }

    @Test
    public void update() {

        Product product = new Product();
        product.setId(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        File file = new File();
        file.setId(UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));

        ProductFile productFileBeforeUpdate = this.repository.findById(PRODUCTFILE_ID).get();

        assertThat(productFileBeforeUpdate.getId()).isEqualTo(PRODUCTFILE_ID);
        assertThat(productFileBeforeUpdate.getProduct().getId())
                .isEqualByComparingTo(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        assertThat(productFileBeforeUpdate.getFile().getId())
                .isEqualByComparingTo(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        productFileBeforeUpdate.setProduct(product);
        productFileBeforeUpdate.setFile(file);

        ProductFile productContainerAfterUpdate = this.repository.saveAndFlush(productFileBeforeUpdate);

        assertThat(productContainerAfterUpdate.getId()).isEqualTo(PRODUCTFILE_ID);
        assertThat(productContainerAfterUpdate.getProduct().getId())
                .isEqualByComparingTo(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(productContainerAfterUpdate.getFile().getId())
                .isEqualByComparingTo(UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));
    }

    @Test
    public void findOneByProductIdAndListedTrue() {

        ProductFile productFile = this.repository
                .findOneByProductIdAndListedTrue(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        assertThat(productFile.getId()).isEqualTo(UUID.fromString("5394ec25-3e3e-4a9d-ab1a-ca9269001fbe"));
        assertThat(productFile.getFile().getId()).isEqualTo(UUID.fromString("757daf1c-ad4f-4bac-8e0e-b4594d05fb6c"));
    }

    @Test
    public void findAllByProductId() {

        Product product = new Product();
        product.setId(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        List<ProductFile> listproductFile = this.repository
                .findAllByProductId(product.getId());

        assertThat(listproductFile).hasSize(2);
        assertThat(listproductFile).extracting(ProductFile::getId)
                .containsExactlyInAnyOrder(UUID.fromString("5394ec25-3e3e-4a9d-ab1a-ca9269001fbe"),
                        UUID.fromString("e9e2dfa3-7496-48a5-a64c-42eb3c52c237"));
        assertThat(listproductFile).extracting(pf -> pf.getProduct().getId())
                .containsExactlyInAnyOrder(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
        assertThat(listproductFile).extracting(pf -> pf.getFile().getId())
                .containsExactlyInAnyOrder(UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"),
                        UUID.fromString("757daf1c-ad4f-4bac-8e0e-b4594d05fb6c"));
        assertThat(listproductFile).extracting(ProductFile::getListed)
                .containsExactlyInAnyOrder(true, false);
    }
}




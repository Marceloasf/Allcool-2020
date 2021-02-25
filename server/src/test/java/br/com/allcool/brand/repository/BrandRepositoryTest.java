package br.com.allcool.brand.repository;

import br.com.allcool.brand.domain.Brand;
import br.com.allcool.file.domain.File;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/file/file.sql","/sql/brand/brand.sql"})
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository repository;

    private final UUID BRAND_ID = UUID.fromString("c86c2e22-c34b-4c0c-aded-055d0c0e696a");

    @Test
    public void findAll() {

        List<Brand> brandList = this.repository.findAll();

        assertThat(brandList).hasSize(2);
        assertThat(brandList).extracting(Brand::getName).containsExactlyInAnyOrder("Brand Zero","Brand One");
    }

    @Test
    public void delete() {

        List<Brand> brandListBeforeDelete = this.repository.findAll();

        assertThat(brandListBeforeDelete).hasSize(2);

        this.repository.deleteById(BRAND_ID);

        List<Brand> brandListAfterDelete = this.repository.findAll();

        assertThat(brandListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        File file = new File();
        file.setUrl("www.filetest.com");
        file.setType("Type");
        file.setName("File Test");
        file.setDate(LocalDateTime.of(2020,05,23,18,05,20));

        Brand brand = new Brand();
        brand.setName("Brand Save");
        brand.setFile(file);

        Brand savedBrand = this.repository.saveAndFlush(brand);

        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo(brand.getName());
        assertThat(savedBrand.getFile()).isEqualTo(file);

    }

    @Test
    public void update() {

        File file = new File();
        file.setUrl("www.fileupdate.com.br");
        file.setType("Type Update");
        file.setName("File Test");
        file.setDate(LocalDateTime.of(2020,05,23,01,00,00));

        Brand brandBeforeUpdate = this.repository.findById(BRAND_ID).get();

        assertThat(brandBeforeUpdate.getId()).isEqualTo(BRAND_ID);
        assertThat(brandBeforeUpdate.getName()).isEqualTo("Brand Zero");
        assertThat(brandBeforeUpdate.getFile()).isNotNull();

        brandBeforeUpdate.setFile(file);
        brandBeforeUpdate.setName("Brand Update");

        Brand brandAfterUpdate = this.repository.saveAndFlush(brandBeforeUpdate);

        assertThat(brandAfterUpdate.getId()).isEqualTo(BRAND_ID);
        assertThat(brandAfterUpdate.getName()).isEqualTo("Brand Update");
        assertThat(brandAfterUpdate.getFile().getId()).isNotNull();

    }
}

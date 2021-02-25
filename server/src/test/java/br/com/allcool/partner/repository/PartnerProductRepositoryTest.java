package br.com.allcool.partner.repository;

import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.domain.PartnerProduct;
import br.com.allcool.product.domain.Product;
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
@SuppressWarnings("OptionalGetWithoutIsPresent")
@Sql(scripts = {"/sql/address/address.sql", "/sql/file/file.sql", "/sql/brand/brand.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/partner/partner.sql",
        "/sql/partner/partnerproduct.sql"})
public class PartnerProductRepositoryTest {

    @Autowired
    private PartnerProductRepository repository;

    private final UUID PARTNERPRODUCT_ID = UUID.fromString("b552b192-5282-46dd-8afb-f5181464c044");

    @Test
    public void findAll() {
        List<PartnerProduct> partnerProductList = this.repository.findAll();

        assertThat(partnerProductList).hasSize(2);

        assertThat(partnerProductList).extracting(partnerProduct -> partnerProduct.getPartner().getId())
                .containsExactly(UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720"),
                        UUID.fromString("d0c4238f-a9c9-452b-9c09-315e19d46506"));
        assertThat(partnerProductList).extracting(partnerProduct -> partnerProduct.getProduct().getId())
                .containsExactly(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"),
                        UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
    }

    @Test
    public void delete() {

        List<PartnerProduct> partnerProductListBeforeDelete = this.repository.findAll();

        assertThat(partnerProductListBeforeDelete).hasSize(2);

        this.repository.deleteById(PARTNERPRODUCT_ID);

        List<PartnerProduct> partnerProductListAfterDelete = this.repository.findAll();

        assertThat(partnerProductListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Partner partner = new Partner();
        partner.setId(UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720"));
        partner.setName("Teste parceiro");

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));
        product.setName("Produto");

        PartnerProduct partnerProduct = new PartnerProduct();
        partnerProduct.setPartner(partner);
        partnerProduct.setProduct(product);

        PartnerProduct savedPartnerProduct = this.repository.saveAndFlush(partnerProduct);

        assertThat(savedPartnerProduct.getId()).isNotNull();
        assertThat(savedPartnerProduct.getPartner().getName()).isEqualTo(partner.getName());
        assertThat(savedPartnerProduct.getProduct().getName()).isEqualTo(product.getName());
    }

    @Test
    public void update() {

        Partner partner = new Partner();
        partner.setId(UUID.fromString("d0c4238f-a9c9-452b-9c09-315e19d46506"));

        Product product = new Product();
        product.setId(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));

        PartnerProduct partnerProductBeforeUpdate = this.repository.findById(PARTNERPRODUCT_ID).get();

        assertThat(partnerProductBeforeUpdate.getId()).isEqualTo(PARTNERPRODUCT_ID);
        assertThat(partnerProductBeforeUpdate.getPartner().getId())
                .isEqualByComparingTo(UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720"));
        assertThat(partnerProductBeforeUpdate.getProduct().getId())
                .isEqualByComparingTo(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        partnerProductBeforeUpdate.setPartner(partner);
        partnerProductBeforeUpdate.setProduct(product);

        PartnerProduct partnerProductAfterUpdate = this.repository.saveAndFlush(partnerProductBeforeUpdate);

        assertThat(partnerProductAfterUpdate.getId()).isEqualTo(PARTNERPRODUCT_ID);
        assertThat(partnerProductAfterUpdate.getPartner().getId())
                .isEqualTo(UUID.fromString("d0c4238f-a9c9-452b-9c09-315e19d46506"));
        assertThat(partnerProductAfterUpdate.getProduct().getId())
                .isEqualTo(UUID.fromString("8f50022f-4058-4f8e-8062-fc0ef9bc327e"));
    }
}

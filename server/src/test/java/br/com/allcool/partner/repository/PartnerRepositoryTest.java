package br.com.allcool.partner.repository;

import br.com.allcool.address.domain.Address;
import br.com.allcool.file.domain.File;
import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.domain.PartnerProduct;
import br.com.allcool.partner.domain.WorkingPeriod;
import br.com.allcool.product.domain.Product;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@SuppressWarnings("OptionalGetWithoutIsPresent")
@Sql(scripts = {"/sql/address/address.sql", "/sql/file/file.sql", "/sql/brand/brand.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/partner/partner.sql"})
public class PartnerRepositoryTest {

    @Autowired
    private PartnerRepository repository;

    private final UUID PARTNER_ID = UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720");

    @Test
    public void findAll() {
        List<Partner> partnerList = this.repository.findAll();

        assertThat(partnerList).hasSize(2);

        assertThat(partnerList).extracting(partner -> partner.getAddress().getId())
                .containsExactly(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"),
                        UUID.fromString("28fd5d3c-97b2-11ea-bb37-0242ac130002"));
        assertThat(partnerList).extracting(partner -> partner.getFile().getId())
                .containsExactly(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"),
                        UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));
        assertThat(partnerList).extracting(Partner::getName)
                .containsExactly("Teste parceiro", "Teste parceiro 2");
        assertThat(partnerList).extracting(Partner::getDescription)
                .containsExactly("Lugar com ambiente agradavel", "Local agradavel teste");
        assertThat(partnerList).extracting(Partner::getPhoneNumber)
                .containsExactly("992448023", "995448027");
    }

    @Test
    public void delete() {

        List<Partner> partnerListBeforeDelete = this.repository.findAll();

        assertThat(partnerListBeforeDelete).hasSize(2);

        this.repository.deleteById(PARTNER_ID);

        List<Partner> partnerListAfterDelete = this.repository.findAll();

        assertThat(partnerListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Address address = new Address();
        address.setId(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"));

        File file = new File();
        file.setId(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        Product product = new Product();
        product.setId(UUID.fromString("14d304d3-c965-4875-8f53-86d20bb7d0aa"));

        Partner partner = new Partner();
        partner.setAddress(address);
        partner.setFile(file);
        partner.setName("Teste parceiro");
        partner.setDescription("Lugar com ambiente agradavel");
        partner.setPhoneNumber("992448023");

        PartnerProduct partnerProduct = new PartnerProduct();
        partnerProduct.setPartner(partner);
        partnerProduct.setProduct(product);

        partner.getProducts().add(partnerProduct);

        WorkingPeriod workingPeriod = new WorkingPeriod();
        workingPeriod.setPartner(partner);
        workingPeriod.setDay("teste dia da semana");
        workingPeriod.setOpeningTime(LocalTime.of(18, 45, 00));
        workingPeriod.setClosingTime(LocalTime.of(00, 45, 00));

        partner.getWorkingPeriods().add(workingPeriod);

        Partner savedPartner = this.repository.saveAndFlush(partner);

        assertThat(savedPartner.getId()).isNotNull();
        assertThat(savedPartner.getAddress()).isEqualTo(partner.getAddress());
        assertThat(savedPartner.getFile()).isEqualTo(partner.getFile());
        assertThat(savedPartner.getName()).isEqualTo(partner.getName());
        assertThat(savedPartner.getDescription()).isEqualTo(partner.getDescription());
        assertThat(savedPartner.getPhoneNumber()).isEqualTo(partner.getPhoneNumber());
    }

    @Test
    public void update() {

        Partner partnerBeforeUpdate = this.repository.findById(PARTNER_ID).get();

        assertThat(partnerBeforeUpdate.getId()).isEqualTo(PARTNER_ID);
        assertThat(partnerBeforeUpdate.getDescription()).isEqualTo("Lugar com ambiente agradavel");

        partnerBeforeUpdate.setDescription("Lugar bacana");

        Partner partnerAfterUpdate = this.repository.saveAndFlush(partnerBeforeUpdate);

        assertThat(partnerAfterUpdate.getId()).isEqualTo(PARTNER_ID);
        assertThat(partnerAfterUpdate.getDescription()).isEqualTo("Lugar bacana");
    }
}

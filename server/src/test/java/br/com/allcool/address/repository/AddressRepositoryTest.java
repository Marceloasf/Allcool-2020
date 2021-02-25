package br.com.allcool.address.repository;

import br.com.allcool.address.domain.Address;
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
@Sql(scripts = {"/sql/address/address.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    private final UUID ADDRESS_ID = UUID.fromString("28fd5d3c-97b2-11ea-bb37-0242ac130002");

    @Test
    public void findAll() {

        List<Address> addressList = this.repository.findAll();

        assertThat(addressList).hasSize(2);
        assertThat(addressList).extracting(Address::getZipCode).containsExactly("87060-026", "87060-023");
        assertThat(addressList).extracting(Address::getPublicPlace).containsExactly("Rua Luiza Zequim", "Rua Teste Update");
        assertThat(addressList).extracting(Address::getDistrict).containsExactly("Jardim Santa Rosa", "Bairro Teste");
        assertThat(addressList).extracting(Address::getLocality).containsExactly("Maringa", "Cidade Teste");
        assertThat(addressList).extracting(Address::getFederatedUnit).containsExactly("PR", "RR");
    }

    @Test
    public void delete() {

        List<Address> addressListBeforeDelete = this.repository.findAll();

        assertThat(addressListBeforeDelete).hasSize(2);

        this.repository.deleteById(ADDRESS_ID);

        List<Address> addressListAfterDelete = this.repository.findAll();

        assertThat(addressListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Address address = new Address();
        address.setZipCode("87060-096");
        address.setPublicPlace("Rua do Teste, 28");
        address.setDistrict("Jardim Teste");
        address.setLocality("Maringa");
        address.setFederatedUnit("PR");

        Address savedAddress = this.repository.saveAndFlush(address);

        assertThat(savedAddress.getId()).isNotNull();
        assertThat(savedAddress.getZipCode()).isEqualTo(address.getZipCode());
        assertThat(savedAddress.getPublicPlace()).isEqualTo(address.getPublicPlace());
        assertThat(savedAddress.getDistrict()).isEqualTo(address.getDistrict());
        assertThat(savedAddress.getLocality()).isEqualTo(address.getLocality());
        assertThat(savedAddress.getFederatedUnit()).isEqualTo(address.getFederatedUnit());
    }

    @Test
    public void update() {

        Address addressBeforeUpdate = this.repository.findById(ADDRESS_ID).get();

        assertThat(addressBeforeUpdate.getId()).isEqualTo(ADDRESS_ID);
        assertThat(addressBeforeUpdate.getZipCode()).isEqualTo("87060-023");
        assertThat(addressBeforeUpdate.getPublicPlace()).isEqualTo("Rua Teste Update");
        assertThat(addressBeforeUpdate.getDistrict()).isEqualTo("Bairro Teste");
        assertThat(addressBeforeUpdate.getLocality()).isEqualTo("Cidade Teste");
        assertThat(addressBeforeUpdate.getFederatedUnit()).isEqualTo("RR");

        addressBeforeUpdate.setZipCode("87060-024");
        addressBeforeUpdate.setPublicPlace("Rua Teste Atualizada");
        addressBeforeUpdate.setDistrict("Bairo Teste Atualizado");
        addressBeforeUpdate.setLocality("Cidade Teste Atualizada");
        addressBeforeUpdate.setFederatedUnit("BA");

        Address addressAfterUpdate = this.repository.saveAndFlush(addressBeforeUpdate);

        assertThat(addressAfterUpdate.getId()).isEqualTo(ADDRESS_ID);
        assertThat(addressAfterUpdate.getZipCode()).isEqualTo("87060-024");
        assertThat(addressAfterUpdate.getPublicPlace()).isEqualTo("Rua Teste Atualizada");
        assertThat(addressAfterUpdate.getDistrict()).isEqualTo("Bairo Teste Atualizado");
        assertThat(addressAfterUpdate.getLocality()).isEqualTo("Cidade Teste Atualizada");
        assertThat(addressAfterUpdate.getFederatedUnit()).isEqualTo("BA");
    }
}

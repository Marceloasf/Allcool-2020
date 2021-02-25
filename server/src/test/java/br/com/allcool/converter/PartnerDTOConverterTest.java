package br.com.allcool.converter;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.PartnerDTO;
import br.com.allcool.partner.domain.Partner;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerDTOConverterTest {

	private final PartnerDTOConverter dtoConverter = new PartnerDTOConverter();

    @Test
    public void to() {
    	
    	Address address = new Address();
    	address.setId(UUID.randomUUID());
    	address.setLocality("Maringa");
    	address.setDistrict("Zona 7");
    	address.setFederatedUnit("PR");
    	address.setPublicPlace("Av. Paranagua, 138");

    	Partner partner = new Partner();
    	partner.setId(UUID.randomUUID());
    	partner.setName("MPB Bar");
    	partner.setDescription("Bar de Emo");
        partner.setPhoneNumber("992448023");
        partner.setAddress(address);

        PartnerDTO dto = this.dtoConverter.to(partner);

        assertThat(dto.getId()).isEqualTo(partner.getId());
        assertThat(dto.getName()).isEqualTo(partner.getName());
        assertThat(dto.getPhoneNumber()).isEqualTo(partner.getPhoneNumber());
        assertThat(dto.getAddress().getLocality()).isEqualTo("Maringa");
    }
}
package br.com.allcool.partner.service;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.PartnerDTO;
import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.repository.PartnerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PartnerServiceTest {

    @InjectMocks
    private PartnerService service;

    @Mock
    private PartnerRepository repository;

    @Test
    public void findById() {

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setLocality("Maringa");
        address.setDistrict("Zona 7");
        address.setFederatedUnit("PR");
        address.setPublicPlace("Av. Paranagua, 138");

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("Teste parceiro");
        partner.setAddress(address);
        partner.setPhoneNumber("992448023");

        when(this.repository.findById(partner.getId())).thenReturn(Optional.of(partner));

        this.service.findById(partner.getId());

        verify(this.repository).findById(partner.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findAll() {

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setLocality("Maringa");
        address.setDistrict("Zona 7");
        address.setFederatedUnit("PR");
        address.setPublicPlace("Av. Paranagua, 138");

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("Teste parceiro");
        partner.setAddress(address);
        partner.setPhoneNumber("992448023");

        when(this.repository.findAll()).thenReturn(Collections.singletonList(partner));

        List<PartnerDTO> result = this.service.findAll();

        assertThat(result).extracting(PartnerDTO::getId).containsExactly(partner.getId());
        assertThat(result).extracting(PartnerDTO::getPhoneNumber).containsExactly(partner.getPhoneNumber());
        assertThat(result).extracting(PartnerDTO::getName).containsExactly(partner.getName());
        assertThat(result).extracting(PartnerDTO::getAddress).containsExactly(partner.getAddress());

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }
}

package br.com.allcool.converter;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.FileDTO;
import br.com.allcool.dto.PartnerViewDTO;
import br.com.allcool.file.domain.File;
import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.domain.WorkingPeriod;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerViewDTOConverterTest {

    private final PartnerViewDTOConverter converter = new PartnerViewDTOConverter();
    private final WorkingPeriodDTOConverter workingPeriodDTOConverter = new WorkingPeriodDTOConverter();

    @Test
    public void to() {

        File file = new File();
        file.setId(UUID.randomUUID());
        file.setUrl("www.teste.com");

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setLocality("Maringa");
        address.setDistrict("Zona 7");
        address.setFederatedUnit("PR");
        address.setPublicPlace("Av. Paranagua, 138");

        WorkingPeriod workingPeriod = new WorkingPeriod();
        workingPeriod.setId(UUID.randomUUID());
        workingPeriod.setDay("Terça");
        workingPeriod.setOpeningTime(LocalTime.of(16, 30));
        workingPeriod.setClosingTime(LocalTime.MIN);

        List<WorkingPeriod> workingPeriodList = new ArrayList<>();
        workingPeriodList.add(workingPeriod);

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("MPB Bar");
        partner.setDescription("Bar de Emo, que o Gabriel gosta");
        partner.setPhoneNumber("992448023");
        partner.setFile(file);
        partner.setWorkingPeriods(workingPeriodList);
        partner.setAddress(address);

        PartnerViewDTO dto = this.converter.to(partner);

        assertThat(dto.getId()).isEqualTo(partner.getId());
        assertThat(dto.getName()).isEqualTo(partner.getName());
        assertThat(dto.getDescription()).isEqualTo(partner.getDescription());
        assertThat(dto.getPhoneNumber()).isEqualTo(partner.getPhoneNumber());
        assertThat(dto.getFileDTO()).isEqualTo(new FileDTO(file.getId(), file.getUrl()));
        assertThat(dto.getWorkingPeriodDTOList()).containsExactlyInAnyOrder(this.workingPeriodDTOConverter.to(workingPeriod));
        assertThat(dto.getLocality()).isEqualTo("Maringa - PR");
        assertThat(dto.getAddress()).isEqualTo("Av. Paranagua, 138 - Zona 7");
    }
}

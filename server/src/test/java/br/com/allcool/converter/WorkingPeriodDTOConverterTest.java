package br.com.allcool.converter;

import br.com.allcool.dto.WorkingPeriodDTO;
import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.domain.WorkingPeriod;
import org.junit.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkingPeriodDTOConverterTest {

    private final WorkingPeriodDTOConverter converter = new WorkingPeriodDTOConverter();

    @Test
    public void to() {

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());

        WorkingPeriod workingPeriod = new WorkingPeriod();
        workingPeriod.setId(UUID.randomUUID());
        workingPeriod.setClosingTime(LocalTime.now());
        workingPeriod.setOpeningTime(LocalTime.now());
        workingPeriod.setDay("monday");
        workingPeriod.setPartner(partner);

        WorkingPeriodDTO dto = this.converter.to(workingPeriod);

        assertThat(dto.getId()).isEqualTo(workingPeriod.getId());
        assertThat(dto.getDay()).isEqualTo(workingPeriod.getDay());
        assertThat(dto.getOpeningTime()).isEqualTo(workingPeriod.getOpeningTime());
        assertThat(dto.getClosingTime()).isEqualTo(workingPeriod.getClosingTime());
    }
}

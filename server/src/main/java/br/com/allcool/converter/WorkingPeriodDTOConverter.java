package br.com.allcool.converter;

import java.util.Objects;

import br.com.allcool.dto.WorkingPeriodDTO;
import br.com.allcool.partner.domain.WorkingPeriod;

public class WorkingPeriodDTOConverter {

    public WorkingPeriodDTO to(WorkingPeriod workingPeriod) {

        WorkingPeriodDTO dto = new WorkingPeriodDTO();

        if (Objects.isNull(workingPeriod)) {
            return dto;
        }

        dto.setId(workingPeriod.getId());
        dto.setClosingTime(workingPeriod.getClosingTime());
        dto.setDay(workingPeriod.getDay());
        dto.setOpeningTime(workingPeriod.getOpeningTime());

        return dto;
    }
}

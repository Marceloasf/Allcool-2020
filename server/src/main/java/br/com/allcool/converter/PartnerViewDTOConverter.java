package br.com.allcool.converter;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.FileDTO;
import br.com.allcool.dto.PartnerViewDTO;
import br.com.allcool.dto.WorkingPeriodDTO;
import br.com.allcool.partner.domain.Partner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartnerViewDTOConverter {

    public PartnerViewDTO to(Partner partner) {

        PartnerViewDTO dto = new PartnerViewDTO();

        WorkingPeriodDTOConverter workingPeriodDTOConverter = new WorkingPeriodDTOConverter();

        if (Objects.isNull(partner)) {
            return dto;
        }

        dto.setId(partner.getId());
        dto.setName(partner.getName());
        dto.setDescription(partner.getDescription());
        dto.setPhoneNumber(partner.getPhoneNumber());
        dto.setAddress(buildStringAddress(partner.getAddress()));
        dto.setLocality(buildStringLocality(partner.getAddress()));

        if (Objects.nonNull(partner.getFile())) {
            dto.setFileDTO(new FileDTO(partner.getFile().getId(), partner.getFile().getUrl()));
        }

        List<WorkingPeriodDTO> workingPeriodDTOS = partner.getWorkingPeriods().stream().map(workingPeriodDTOConverter::to).collect(Collectors.toList());

        dto.setWorkingPeriodDTOList(workingPeriodDTOS);

        return dto;
    }

    private String buildStringAddress(Address address) {

        return address.getPublicPlace() + " - " + address.getDistrict();
    }

    private String buildStringLocality(Address address) {

        return address.getLocality() + " - " + address.getFederatedUnit();
    }
}

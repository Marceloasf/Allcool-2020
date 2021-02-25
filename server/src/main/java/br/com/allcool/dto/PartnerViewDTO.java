package br.com.allcool.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PartnerViewDTO {

    private UUID id;
    private String name;
    private String description;
    private String phoneNumber;
    private FileDTO fileDTO;
    private List<WorkingPeriodDTO> workingPeriodDTOList;
    private String address;
    private String locality;
}

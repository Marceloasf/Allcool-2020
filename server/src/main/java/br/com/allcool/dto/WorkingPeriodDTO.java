package br.com.allcool.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class WorkingPeriodDTO {

    private UUID id;
    private String day;
    private LocalTime openingTime;
    private LocalTime closingTime;
}

package br.com.allcool.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserClientDTO {

    private UUID id;
    private String name;
    private FileDTO userPicture;
    private String bio;
    private String location;
    private LocalDate birthDate;
}

package br.com.allcool.dto;

import java.util.UUID;

import br.com.allcool.address.domain.Address;
import lombok.Data;

@Data
public class PartnerDTO {

	 private UUID id;
	 private String name;
	 private String phoneNumber;
	 private Address address;
	 private String avatarUrl;
}

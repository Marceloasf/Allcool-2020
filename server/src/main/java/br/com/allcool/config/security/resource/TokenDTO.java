package br.com.allcool.config.security.resource;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

	private String token;
	private String tipoAuth;
	private UUID userId;
	
}

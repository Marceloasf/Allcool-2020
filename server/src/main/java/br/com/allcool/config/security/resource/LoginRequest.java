package br.com.allcool.config.security.resource;

import lombok.Data;

@Data
public class LoginRequest {

	private String email;
	private String password;
}

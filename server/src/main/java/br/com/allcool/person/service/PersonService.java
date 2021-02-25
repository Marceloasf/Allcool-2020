package br.com.allcool.person.service;

import br.com.allcool.config.security.TokenService;
import br.com.allcool.config.security.resource.LoginRequest;
import br.com.allcool.config.security.resource.TokenDTO;
import br.com.allcool.exception.UserNotFoundException;
import br.com.allcool.person.domain.Person;
import br.com.allcool.person.repository.PersonRepository;
import br.com.allcool.user.domain.UserClient;
import br.com.allcool.user.repository.UserClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final PersonRepository personRepository;
    private final UserClientRepository userClientRepository;

    public ResponseEntity<TokenDTO> login(LoginRequest login) {

        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        try {

            Authentication auth = authManager.authenticate(dadosLogin);
            String token = tokenService.createToken(auth);
            UUID userId = getIdUserLogado(auth);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer", userId));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    public void register(Person person) {

        if (personRepository.existsByEmail(person.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email!");
        }

        String password = person.getPassword();
        person.setPassword(passwordEncoder.encode(password));

        personRepository.save(person);
    }

    private UUID getIdUserLogado(Authentication auth) {

		Person person = (Person) auth.getPrincipal();
		UserClient userClient = userClientRepository.findByPersonId(person.getId())
				.orElseThrow(UserNotFoundException::new);

		return userClient.getId();
	}
}

package br.com.allcool.config.security.resource;

import br.com.allcool.person.domain.Person;
import br.com.allcool.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    @Autowired
    private PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest login) {

        return personService.login(login);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid Person register) {

        personService.register(register);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

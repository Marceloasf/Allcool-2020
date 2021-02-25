package br.com.allcool.config.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.allcool.person.domain.Person;
import br.com.allcool.person.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SuppressWarnings("deprecation")
public class AuthenticationServiceTest {

	@MockBean
	private PersonRepository personRepository;
	
	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	private AuthenticationService authenticationService;
	
	@Before
	public void setup() {
		
		authenticationService = new AuthenticationService(personRepository);
	}
	
	
	@Test
	public void loadUserByUsername() throws IOException {
		
		Person person = new Person();
		person.setId(UUID.fromString("affb9869-61b3-4100-bafd-df7cf46ef341"));
		person.setEmail("teste@hotmail.com");
		when(personRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.of(person));
		
		UserDetails loadUserByUsername = authenticationService.loadUserByUsername("teste@hotmail.com");
		
        assertThat(loadUserByUsername.getUsername()).isEqualTo("teste@hotmail.com");
	}
	
	@Test
	public void loadUserByUsernameNotFound() throws IOException {
		
		expectedException.expectMessage("Dados inválidos");
		
		authenticationService.loadUserByUsername("exception@hotmail.com");

	}
}

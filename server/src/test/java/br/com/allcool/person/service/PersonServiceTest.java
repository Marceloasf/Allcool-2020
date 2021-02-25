package br.com.allcool.person.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.allcool.config.security.TokenService;
import br.com.allcool.person.domain.Person;
import br.com.allcool.person.repository.PersonRepository;
import br.com.allcool.user.repository.UserClientRepository;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

	@Mock
	private AuthenticationManager authManager;
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private UserClientRepository userClientRepository;
	
	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	private PersonService service;
	
	@Before
	public void setup() {
		
		service = new PersonService(authManager, tokenService, passwordEncoder, personRepository, userClientRepository);
	}
	
	@Test
	public void register() {
		
		when(personRepository.existsByEmail(ArgumentMatchers.any())).thenReturn(Boolean.FALSE);
		when(personRepository.save(ArgumentMatchers.any())).thenReturn(new Person());
		
		service.register(new Person());
		
		verify(personRepository, times(1)).save(ArgumentMatchers.any());
	}
	
	@Test
	public void registerWhenEmailExits() {
		
		expectedException.expectMessage("Já existe um usuário cadastrado com esse email!");
		
		when(personRepository.existsByEmail(ArgumentMatchers.any())).thenReturn(Boolean.TRUE);
		service.register(new Person());
	}
	
}

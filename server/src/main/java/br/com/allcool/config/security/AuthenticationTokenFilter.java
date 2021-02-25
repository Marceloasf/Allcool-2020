package br.com.allcool.config.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import br.com.allcool.person.domain.Person;
import br.com.allcool.person.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER = "Bearer ";
    private static final Integer START_TOKEN = 7;
	
	private TokenService tokenService;
	private PersonRepository personRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = resolveToken(request);
	
		if(tokenService.validateToken(token)) {			
			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String resolveToken(HttpServletRequest request ) {
		
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);	
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {		
			return bearerToken.substring(START_TOKEN, bearerToken.length());
		}
		
		return null;
	}
	
	private Authentication getAuthentication(String token) {
		
		Claims claims = tokenService.parseClaims(token);
		UUID idPerson = UUID.fromString(claims.getSubject());
		Person person = personRepository.findById(idPerson).get();
		
		return new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities());			
	}

}

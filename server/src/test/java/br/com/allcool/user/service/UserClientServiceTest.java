package br.com.allcool.user.service;

import br.com.allcool.exception.UserNotFoundException;
import br.com.allcool.file.domain.File;
import br.com.allcool.person.domain.Person;
import br.com.allcool.user.domain.UserClient;
import br.com.allcool.user.repository.UserClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserClientServiceTest {

    @Mock
    private UserClientRepository repository;

    @InjectMocks
    private UserClientService service;

    @Test
    public void findUserClientDTOByPersonId() {

        UUID userId = UUID.randomUUID();

        UserClient userClient = new UserClient();
        userClient.setId(userId);
        userClient.setPerson(new Person());
        userClient.setFile(new File());

        when(this.repository.findById(userId)).thenReturn(Optional.of(userClient));

        this.service.findById(userId);

        verify(this.repository).findById(userId);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findUserClientDTOByPersonNotFoundException() {

        UUID userId = UUID.randomUUID();

        when(this.repository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class,
                () -> this.service.findById(userId));

        assertThat(exception.getMessage())
                .isEqualTo("O usuário não foi encontrado.");
    }
}
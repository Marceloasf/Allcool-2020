package br.com.allcool.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class UserNotFoundExceptionTest {

    @Test
    public void shouldThrowException() {

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException();
        });

        assertThat(exception.getMessage()).isEqualTo("O usuário não foi encontrado.");
    }
}
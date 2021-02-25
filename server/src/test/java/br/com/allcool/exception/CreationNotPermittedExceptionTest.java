package br.com.allcool.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class CreationNotPermittedExceptionTest {

    @Test
    public void shouldThrowException() {

        Exception exception = assertThrows(CreationNotPermittedException.class, () -> {
            throw new CreationNotPermittedException("Registro inválido.");
        });

        assertThat(exception.getMessage()).isEqualTo("Não foi possível salvar o registro. Motivo: Registro inválido.");
    }
}
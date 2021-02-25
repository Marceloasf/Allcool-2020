package br.com.allcool.exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataNotFoundExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowException() {

        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("O registro não foi encontrado.");

        throw new DataNotFoundException();
    }
}
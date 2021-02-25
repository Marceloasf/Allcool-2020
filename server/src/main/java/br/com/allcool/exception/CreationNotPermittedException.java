package br.com.allcool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CreationNotPermittedException extends RuntimeException {

    public CreationNotPermittedException(String message) {
        super("Não foi possível salvar o registro. Motivo: " + message);
    }
}

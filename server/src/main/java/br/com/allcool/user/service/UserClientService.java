package br.com.allcool.user.service;

import br.com.allcool.converter.UserClientDTOConverter;
import br.com.allcool.dto.UserClientDTO;
import br.com.allcool.exception.UserNotFoundException;
import br.com.allcool.user.repository.UserClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserClientService {

    private final UserClientRepository repository;

    public UserClientService(UserClientRepository repository) {

        this.repository = repository;
    }

    public UserClientDTO findById(UUID id) {

        return new UserClientDTOConverter().to(this.repository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }
}

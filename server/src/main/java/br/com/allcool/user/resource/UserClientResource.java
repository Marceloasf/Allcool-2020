package br.com.allcool.user.resource;

import br.com.allcool.dto.UserClientDTO;
import br.com.allcool.user.service.UserClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserClientResource {

    private final UserClientService service;

    public UserClientResource(UserClientService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserClientDTO> findById(@PathVariable("userId") UUID id) {

        return ResponseEntity.ok(this.service.findById(id));
    }
}

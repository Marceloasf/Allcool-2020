package br.com.allcool.publication.resource;

import br.com.allcool.dto.PublicationDTO;
import br.com.allcool.publication.service.PublicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
public class PublicationResource {

    private final PublicationService service;

    public PublicationResource(PublicationService service) {

        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> findAll() {

        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PublicationDTO>> findAllReviewPublicationsByUserId(@PathVariable("id") UUID userId) {

        return ResponseEntity.ok(this.service.findAllReviewPublicationsByUserId(userId));
    }
}

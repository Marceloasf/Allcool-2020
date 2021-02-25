package br.com.allcool.partner.resource;

import br.com.allcool.dto.PartnerDTO;
import br.com.allcool.dto.PartnerViewDTO;
import br.com.allcool.partner.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/partners")
public class PartnerResource {

    private final PartnerService service;

    public PartnerResource(PartnerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerViewDTO> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PartnerDTO>> findAll() {

        return ResponseEntity.ok(this.service.findAll());
    }
}

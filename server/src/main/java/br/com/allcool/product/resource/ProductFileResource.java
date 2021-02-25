package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductFileDTO;
import br.com.allcool.product.service.ProductFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products-files")
public class ProductFileResource {

    private final ProductFileService service;

    public ProductFileResource(ProductFileService service) {
        this.service = service;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductFileDTO>> findAllByProductId(@PathVariable("productId") UUID id) {

        return ResponseEntity.ok(this.service.findAllByProductId(id));
    }
}

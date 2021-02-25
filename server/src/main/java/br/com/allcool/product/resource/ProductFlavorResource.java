package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.product.service.ProductFlavorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products-flavors")
public class ProductFlavorResource {

    private final ProductFlavorService service;

    public ProductFlavorResource(ProductFlavorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductFlavorDTO>> findAllByProductId(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(this.service.findAllByProductId(id));
    }
}

package br.com.allcool.product.resource;

import br.com.allcool.dto.ProductDTO;
import br.com.allcool.product.domain.Product;
import br.com.allcool.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {

        return ResponseEntity.ok(this.service.findAll());
    }
}

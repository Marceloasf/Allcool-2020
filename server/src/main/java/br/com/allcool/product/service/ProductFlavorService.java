package br.com.allcool.product.service;

import br.com.allcool.dto.ProductFlavorDTO;
import br.com.allcool.product.repository.ProductFlavorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductFlavorService {

    private final ProductFlavorRepository repository;

    public ProductFlavorService(ProductFlavorRepository repository) {
        this.repository = repository;
    }

    public List<ProductFlavorDTO> findAllByProductId(UUID productId) {

        return this.repository.findAllByProductId(productId).stream()
                .map(pf -> new ProductFlavorDTO(pf.getId(), pf.getDescription(), pf.getType()))
                .sorted(Comparator.comparing(ProductFlavorDTO::getDescription))
                .collect(Collectors.toList());
    }
}

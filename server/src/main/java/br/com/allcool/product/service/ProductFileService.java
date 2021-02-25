package br.com.allcool.product.service;

import br.com.allcool.dto.ProductFileDTO;
import br.com.allcool.product.repository.ProductFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductFileService {

    private final ProductFileRepository productFileRepository;

    public ProductFileService(ProductFileRepository productFileRepository) {
        this.productFileRepository = productFileRepository;
    }

    public List<ProductFileDTO> findAllByProductId(UUID productId) {

        return this.productFileRepository
                .findAllByProductId(productId).stream()
                .map(productFile -> new ProductFileDTO(productFile.getId(), productFile.getFile().getUrl()))
                .collect(Collectors.toList());
    }
}

package br.com.allcool.product.repository;

import br.com.allcool.product.domain.ProductFlavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductFlavorRepository extends JpaRepository<ProductFlavor, UUID> {

    List<ProductFlavor> findAllByProductId(UUID productId);
}

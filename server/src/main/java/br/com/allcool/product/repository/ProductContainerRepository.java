package br.com.allcool.product.repository;

import br.com.allcool.product.domain.ProductContainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductContainerRepository extends JpaRepository<ProductContainer, UUID> {
}

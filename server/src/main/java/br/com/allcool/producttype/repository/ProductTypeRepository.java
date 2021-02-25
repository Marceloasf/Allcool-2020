package br.com.allcool.producttype.repository;


import br.com.allcool.producttype.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
}

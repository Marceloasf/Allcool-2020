package br.com.allcool.product.repository;

import br.com.allcool.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select coalesce(avg(r.rating), 0) from Review r where r.product.id = :productId")
    BigDecimal getProductAverageRating(@Param("productId") UUID productId);
}

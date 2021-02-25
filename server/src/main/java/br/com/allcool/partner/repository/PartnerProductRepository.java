package br.com.allcool.partner.repository;

import br.com.allcool.partner.domain.PartnerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartnerProductRepository extends JpaRepository<PartnerProduct, UUID> {
}

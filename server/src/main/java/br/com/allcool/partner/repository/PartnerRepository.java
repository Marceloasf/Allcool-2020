package br.com.allcool.partner.repository;

import br.com.allcool.partner.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartnerRepository extends JpaRepository<Partner, UUID> {
}

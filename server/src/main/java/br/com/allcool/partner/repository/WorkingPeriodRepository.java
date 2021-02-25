package br.com.allcool.partner.repository;

import br.com.allcool.partner.domain.WorkingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkingPeriodRepository extends JpaRepository<WorkingPeriod, UUID> {
}

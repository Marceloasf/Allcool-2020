package br.com.allcool.user.repository;

import br.com.allcool.user.domain.UserClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserClientProductRepository extends JpaRepository<UserClientProduct, UUID> {
}

package br.com.allcool.achievement.resource;

import br.com.allcool.achievement.service.AchievementService;
import br.com.allcool.dto.AchievementDTO;
import br.com.allcool.dto.AchievementViewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/achievements")
public class AchievementResource {

    private final AchievementService service;

    public AchievementResource(AchievementService service) {
        this.service = service;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<AchievementDTO>> findAllAchievementByProductId(@PathVariable("productId") UUID id) {

        return ResponseEntity.ok(this.service.findAllAchievementByProductId(id));
    }

    @GetMapping("/product-types/{productTypeId}")
    public ResponseEntity<List<AchievementDTO>> findAllByProductTypeId(@PathVariable("productTypeId") UUID productTypeId) {

        return ResponseEntity.ok(this.service.findAllByProductTypeId(productTypeId));
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<AchievementDTO>> findAllByBrandId(@PathVariable("brandId") UUID brandId) {

        return ResponseEntity.ok(this.service.findAllByBrandId(brandId));
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<AchievementViewDTO> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping("/products/{productId}")
    public Long countByProductId(UUID productId) {

        return service.countByProductId(productId);
    }

}

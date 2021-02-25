package br.com.allcool.achievement.service;

import br.com.allcool.achievement.repository.AchievementRepository;
import br.com.allcool.converter.AchievementDTOConverter;
import br.com.allcool.converter.AchievementViewDTOConverter;
import br.com.allcool.dto.AchievementDTO;
import br.com.allcool.dto.AchievementViewDTO;
import br.com.allcool.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AchievementService {

    private final AchievementRepository repository;

    public AchievementService(AchievementRepository achievementRepository) {
        this.repository = achievementRepository;
    }

    public List<AchievementDTO> findAllAchievementByProductId(UUID productId) {

        AchievementDTOConverter converter = new AchievementDTOConverter();

        return this.repository.findAllAchievementByProductId(productId).stream()
                .map(converter::toBrandAchievement).collect(Collectors.toList());

    }

    public List<AchievementDTO> findAllByProductTypeId(UUID productTypeId) {

        AchievementDTOConverter converter = new AchievementDTOConverter();

        return this.repository.findAllByProductTypeId(productTypeId).stream()
                .map(converter::toBrandAchievement).collect(Collectors.toList());
    }

    public List<AchievementDTO> findAllByBrandId(UUID brandId) {

        AchievementDTOConverter converter = new AchievementDTOConverter();

        return this.repository.findAllByBrandId(brandId).stream()
                .map(converter::toBrandAchievement).collect(Collectors.toList());
    }

    public AchievementViewDTO findById(UUID id) {

        return new AchievementViewDTOConverter().to(this.repository.findById(id).orElseThrow(DataNotFoundException::new));

    }

    public Long countByProductId(UUID productId) {

        return repository.countByProductId(productId);

    }
}

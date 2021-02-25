package br.com.allcool.publication.service;

import br.com.allcool.converter.PublicationDTOConverter;
import br.com.allcool.dto.PublicationDTO;
import br.com.allcool.publication.repository.PublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PublicationService {

    private final PublicationRepository repository;
    private final PublicationDTOConverter dtoConverter = new PublicationDTOConverter();

    public PublicationService(PublicationRepository repository) {

        this.repository = repository;
    }

    public List<PublicationDTO> findAll() {

        return this.repository.findAll().stream().map(dtoConverter::to)
                .sorted(Comparator.comparing(PublicationDTO::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findAllReviewPublicationsByUserId(UUID userId) {

        return this.repository.findAllByReviewUserId(userId)
                .stream().map(dtoConverter::to)
                .sorted(Comparator.comparing(PublicationDTO::getCreationDate).reversed())
                .collect(Collectors.toList());
    }
}

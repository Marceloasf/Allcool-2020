package br.com.allcool.review.service;

import br.com.allcool.converter.ReviewDTOConverter;
import br.com.allcool.converter.ReviewFormDTOConverter;
import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.exception.CreationNotPermittedException;
import br.com.allcool.exception.DataNotFoundException;
import br.com.allcool.file.domain.File;
import br.com.allcool.file.service.FileService;
import br.com.allcool.publication.domain.Publication;
import br.com.allcool.publication.repository.PublicationRepository;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository repository;
    private final PublicationRepository publicationRepository;
    private final FileService fileService;

    public ReviewService(ReviewRepository repository, PublicationRepository publicationRepository,
                         FileService fileService) {
        this.repository = repository;
        this.publicationRepository = publicationRepository;
        this.fileService = fileService;
    }

    public List<ReviewDTO> findAllByProductId(UUID productId) {

        ReviewDTOConverter converter = new ReviewDTOConverter();

        return this.repository.findAllByProductId(productId).stream()
                .sorted(Comparator.comparing(Review::getCreationDate).reversed())
                .map(converter::to).collect(Collectors.toList());
    }

    private void verifyReviewFormDTOConsistency(ReviewFormDTO dto) {

        if (isNull(dto.getProductId())) {
            throw new CreationNotPermittedException("Registro sem produto vínculado. Atualize e tente novamente.");
        }

        if (isNull(dto.getUserClientId())) {
            throw new CreationNotPermittedException("Registro sem usuário vínculado. Atualize e tente novamente.");
        }

        if (dto.getFlavors().isEmpty()) {
            throw new CreationNotPermittedException("Nenhum sabor foi selecionado.");
        }

        if (this.repository.existsByUserIdAndProductId(dto.getUserClientId(), dto.getProductId())) {
            throw new CreationNotPermittedException("O usuário logado já fez uma avaliação para o produto selecionado.");
        }
    }

    @Transactional
    public Review saveReview(ReviewFormDTO dto) {

        this.verifyReviewFormDTOConsistency(dto);

        Review review = this.repository.saveAndFlush(new ReviewFormDTOConverter().from(dto));

        this.publicationRepository.save(new Publication(review));

        return review;
    }

    public Boolean isProductReviewed(UUID userId, UUID productId) {

        return this.repository.existsByUserIdAndProductId(userId, productId);
    }

    @Transactional(readOnly = true)
    public ReviewDTO findById(UUID id) {

        return new ReviewDTOConverter().to(this.repository.findById(id).orElseThrow(DataNotFoundException::new));
    }

    @Transactional
    public void saveReviewPicture(MultipartFile file, UUID reviewId) throws IOException {

        Review review = this.repository.findById(reviewId).orElseThrow(DataNotFoundException::new);

        final File image = fileService.createImage(file, "reviews", review.getId());

        review.setFile(image);

        this.repository.save(review);
    }
}

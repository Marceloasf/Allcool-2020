package br.com.allcool.review.resource;

import br.com.allcool.dto.ReviewDTO;
import br.com.allcool.dto.ReviewFormDTO;
import br.com.allcool.review.domain.Review;
import br.com.allcool.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/reviews")
public class ReviewResource {

    private final ReviewService service;

    public ReviewResource(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDTO>> findAllByProductId(@PathVariable("productId") UUID id) {

        return ResponseEntity.ok(this.service.findAllByProductId(id));
    }

    @PostMapping
    public ResponseEntity<Review> saveReview(@RequestBody ReviewFormDTO review) {

        return ResponseEntity.ok(this.service.saveReview(review));
    }

    @GetMapping("/products/{productId}/users/{userId}/verify-user-review")
    public ResponseEntity<Boolean> isProductReviewed(
            @PathVariable("productId") UUID productId, @PathVariable("userId") UUID userId) {

        return ResponseEntity.ok(service.isProductReviewed(userId, productId));
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<ReviewDTO> findById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(this.service.findById(id));
    }

    @PostMapping("/{reviewId}/upload-picture")
    public ResponseEntity<Void> saveReviewPicture(@PathVariable("reviewId") UUID reviewId,
                                                  @RequestParam("image") MultipartFile file) throws IOException {

        this.service.saveReviewPicture(file, reviewId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

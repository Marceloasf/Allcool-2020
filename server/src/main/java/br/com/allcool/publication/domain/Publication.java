package br.com.allcool.publication.domain;

import br.com.allcool.enums.PublicationTypeEnum;
import br.com.allcool.news.domain.News;
import br.com.allcool.review.domain.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "publication")
@EqualsAndHashCode(of = "id")
public class Publication {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne
    @JoinColumn(name = "news_id")
    private News news;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "publicationtype")
    private PublicationTypeEnum type;

    @NotNull
    @Column(name = "creationdate")
    private LocalDate creationDate = LocalDate.now();

    private Publication() { }

    public Publication(Review review) {
        this.setReview(review);
        this.setType(PublicationTypeEnum.REVIEW);
    }

    public Publication(News news) {
        this.setNews(news);
        this.setType(PublicationTypeEnum.NEWS);
    }
}

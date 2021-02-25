package br.com.allcool.achievement.domain;

import br.com.allcool.brand.domain.Brand;
import br.com.allcool.enums.AchievementTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.product.domain.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "achievement")
@EqualsAndHashCode(of = "id")
public class Achievement {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    @Length(max = 200)
    private String description;

    @NotNull
    @Column(name = "creationdate")
    private LocalDate creationDate = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "achievementtype")
    private AchievementTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AchievementProductType> productTypes = new ArrayList<>();
}

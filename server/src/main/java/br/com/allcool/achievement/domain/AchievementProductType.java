package br.com.allcool.achievement.domain;

import br.com.allcool.producttype.domain.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "achievementproducttype")
@ToString(exclude = "achievement")
@EqualsAndHashCode(of = {"id", "achievement", "productType"})
public class AchievementProductType {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "producttype_id")
    private ProductType productType;
}

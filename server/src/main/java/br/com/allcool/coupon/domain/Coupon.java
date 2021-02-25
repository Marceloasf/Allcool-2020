package br.com.allcool.coupon.domain;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.enums.CouponTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupon")
@EqualsAndHashCode(of = "id")
public class Coupon {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @NotNull
    private BigDecimal value = BigDecimal.ZERO;

    @NotBlank
    @Length(max = 200)
    private String description;

    @NotNull
    @Column(name = "couponlevel")
    private Long level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coupontype")
    private CouponTypeEnum type;
}

package br.com.allcool.user.domain;

import br.com.allcool.achievement.domain.Achievement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "userclientachievement")
@EqualsAndHashCode(of = {"id", "user", "achievement"})
public class UserClientAchievement {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userclient_id")
    private UserClient user;

    @NotNull
    private Long level;

    @NotNull
    private BigDecimal progress = BigDecimal.ZERO;
}

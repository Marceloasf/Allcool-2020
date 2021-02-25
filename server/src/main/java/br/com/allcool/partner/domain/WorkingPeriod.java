package br.com.allcool.partner.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "workingperiod")
@EqualsAndHashCode(of = {"id", "partner"})
public class WorkingPeriod {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @NotBlank
    @Length(max = 30)
    @Column(name = "weekday")
    private String day;

    @NotNull
    @Column(name = "openingtime")
    private LocalTime openingTime;

    @NotNull
    @Column(name = "closingtime")
    private LocalTime closingTime;
}

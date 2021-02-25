package br.com.allcool.container.domain;

import br.com.allcool.enums.ContainerTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "container")
@EqualsAndHashCode(of = "id")
public class Container {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "containertype")
    private ContainerTypeEnum type;

    @NotNull
    private BigDecimal capacity = BigDecimal.ZERO;
}

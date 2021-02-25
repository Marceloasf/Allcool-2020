package br.com.allcool.user.domain;

import br.com.allcool.product.domain.Product;
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
import java.util.UUID;

@Data
@Entity
@Table(name = "userclientproduct")
@EqualsAndHashCode(of = {"id", "user", "product"})
public class UserClientProduct {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userclient_id")
    private UserClient user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

package br.com.allcool.product.domain;

import br.com.allcool.converter.BooleanToYesOrNo;
import br.com.allcool.file.domain.File;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "productfile")
@EqualsAndHashCode(of = {"id", "product", "file"})
public class ProductFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @NotNull
    @Convert(converter = BooleanToYesOrNo.class)
    private Boolean listed = Boolean.FALSE;
}

package br.com.allcool.partner.domain;

import br.com.allcool.address.domain.Address;
import br.com.allcool.file.domain.File;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "partner")
@EqualsAndHashCode(of = "id")
public class Partner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private File avatar;
    
    @NotBlank
    @Length(max = 100)
    @Column(name = "partnername")
    private String name;

    @NotBlank
    @Length(max = 200)
    private String description;

    @Length(max = 20)
    @Column(name = "phonenumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingPeriod> workingPeriods = new ArrayList<>();

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnerProduct> products = new ArrayList<>();
}

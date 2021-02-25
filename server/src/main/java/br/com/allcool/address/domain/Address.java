package br.com.allcool.address.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "address")
@EqualsAndHashCode(of = "id")
public class Address {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotBlank
    @Length(max = 20)
    @Column(name = "zipcode")
    private String zipCode;

    @NotBlank
    @Length(max = 150)
    @Column(name = "publicplace")
    private String publicPlace;

    @NotBlank
    @Length(max = 60)
    private String district;

    @NotBlank
    @Length(max = 60)
    private String locality;

    @NotBlank
    @Length(max = 2)
    @Column(name = "federatedunit")
    private String federatedUnit;
       
    @Digits(integer = 2, fraction = 7)
    private BigDecimal latitude = BigDecimal.ZERO;
    
    @Digits(integer = 2, fraction = 7)
    private BigDecimal longitude = BigDecimal.ZERO;
 
}
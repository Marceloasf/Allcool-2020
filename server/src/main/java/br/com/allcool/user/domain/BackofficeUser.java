package br.com.allcool.user.domain;

import br.com.allcool.converter.BooleanToYesOrNo;
import br.com.allcool.person.domain.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "backofficeuser")
@EqualsAndHashCode(of = "id")
public class BackofficeUser {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @Convert(converter = BooleanToYesOrNo.class)
    private Boolean moderator = Boolean.FALSE;
}

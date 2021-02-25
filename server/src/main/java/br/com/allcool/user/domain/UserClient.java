package br.com.allcool.user.domain;

import br.com.allcool.file.domain.File;
import br.com.allcool.person.domain.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "userclient")
@EqualsAndHashCode(of = "id")
public class UserClient {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Length(max = 200)
    private String bio;

    @Length(max = 50)
    private String location;

    @NotNull
    @Column(name = "creationdate")
    private LocalDate creationDate = LocalDate.now();
}

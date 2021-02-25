package br.com.allcool.file.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "file")
@EqualsAndHashCode(of = "id")
public class File {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotBlank
    @Length(max = 200)
    private String url;

    @NotBlank
    @Length(max = 45)
    @Column(name = "filetype")
    private String type;

    @NotBlank
    @Length(max = 60)
    @Column(name = "filename")
    private String name;

    @NotNull
    @Column(name = "filedate")
    private LocalDateTime date;
}

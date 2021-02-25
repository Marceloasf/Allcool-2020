package br.com.allcool.file.repository;

import br.com.allcool.file.domain.File;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/file/file.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class FileRepositoryTest {

    @Autowired
    private FileRepository repository;

    private final UUID FILE_ID = UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002");

    @Test
    public void findAll() {

        List<File> fileList = this.repository.findAll();

        assertThat(fileList).hasSize(3);
        assertThat(fileList).extracting(File::getUrl).containsExactly("www.thiago.com.br",
                "www.teste.com.br", "www.teste123.com.br");
        assertThat(fileList).extracting(File::getType).containsExactly("TipoTeste",
                "tipoOutroTeste", "tipoOutroTeste123");
        assertThat(fileList).extracting(File::getName).containsExactly("ArquivoTeste",
                "OutroArquivoTeste", "OutroArquivoTeste123");
        assertThat(fileList).extracting(File::getDate).containsExactly(
                LocalDateTime.of(2020, 5, 14, 1, 0, 0),
                LocalDateTime.of(2020, 6, 12, 0, 0, 1),
                LocalDateTime.of(2020, 6, 12, 0, 0, 1));
    }

    @Test
    public void delete() {

        List<File> fileListBeforeDelete = this.repository.findAll();

        assertThat(fileListBeforeDelete).hasSize(3);

        this.repository.deleteById(FILE_ID);

        List<File> fileListAfterDelete = this.repository.findAll();

        assertThat(fileListAfterDelete).hasSize(2);
    }

    @Test
    public void save() {

        File file = new File();
        file.setUrl("www.thiago.com.br");
        file.setType("TipoTeste");
        file.setName("ArquivoTeste");
        file.setDate(LocalDateTime.of(2020, 05, 14, 20, 43, 20));


        File savedFile = this.repository.saveAndFlush(file);

        assertThat(savedFile.getId()).isNotNull();
        assertThat(savedFile.getUrl()).isEqualTo(file.getUrl());
        assertThat(savedFile.getType()).isEqualTo(file.getType());
        assertThat(savedFile.getName()).isEqualTo(file.getName());
        assertThat(savedFile.getDate()).isEqualTo(file.getDate());
    }

    @Test
    public void update() {

        File fileBeforeUpdate = this.repository.findById(FILE_ID).get();

        assertThat(fileBeforeUpdate.getId()).isEqualTo(FILE_ID);
        assertThat(fileBeforeUpdate.getUrl()).isEqualTo("www.thiago.com.br");
        assertThat(fileBeforeUpdate.getType()).isEqualTo("TipoTeste");
        assertThat(fileBeforeUpdate.getName()).isEqualTo("ArquivoTeste");
        assertThat(fileBeforeUpdate.getDate()).isEqualTo(LocalDateTime.of(2020, 05, 14, 01, 00, 00));

        fileBeforeUpdate.setUrl("www.atualizado.com.br");
        fileBeforeUpdate.setType("novoTypeAtualizado");
        fileBeforeUpdate.setName("ArquivoTesteAtualizado");
        fileBeforeUpdate.setDate(LocalDateTime.of(2000, 2, 1, 20, 43));

        File fileAfterUpdate = this.repository.saveAndFlush(fileBeforeUpdate);

        assertThat(fileAfterUpdate.getId()).isEqualTo(FILE_ID);
        assertThat(fileAfterUpdate.getUrl()).isEqualTo("www.atualizado.com.br");
        assertThat(fileAfterUpdate.getType()).isEqualTo("novoTypeAtualizado");
        assertThat(fileAfterUpdate.getName()).isEqualTo("ArquivoTesteAtualizado");
        assertThat(fileAfterUpdate.getDate()).isEqualTo(LocalDateTime.of(2000, 2, 1, 20, 43));
    }
}

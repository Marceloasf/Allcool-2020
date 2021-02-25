package br.com.allcool.converter;

import br.com.allcool.dto.UserClientDTO;
import br.com.allcool.file.domain.File;
import br.com.allcool.person.domain.Person;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserClientDTOConverterTest {

    private final UserClientDTOConverter converter = new UserClientDTOConverter();

    @Test
    public void to() {

        Person person = new Person();
        person.setName("Fulano");
        person.setBirthDate(LocalDate.of(1999, 1, 1));

        File file = new File();
        file.setId(UUID.randomUUID());

        UserClient user = new UserClient();
        user.setBio("ABC");
        user.setPerson(person);
        user.setFile(file);

        UserClientDTO dto = this.converter.to(user);

        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getBio()).isEqualTo(user.getBio());
        assertThat(dto.getLocation()).isEqualTo(user.getLocation());
        assertThat(dto.getName()).isEqualTo(user.getPerson().getName());
        assertThat(dto.getBirthDate()).isEqualTo(user.getPerson().getBirthDate());
        assertThat(dto.getUserPicture().getId()).isEqualTo(user.getFile().getId());
    }
}
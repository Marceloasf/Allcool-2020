package br.com.allcool.user.repository;

import br.com.allcool.person.domain.Person;
import br.com.allcool.test.RepositoryTest;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/file/file.sql", "/sql/person/person.sql", "/sql/user/userclient.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class UserClientRepositoryTest {

    private final UUID USER_ID = UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c");
    @Autowired
    private UserClientRepository repository;

    @Test
    public void findAll() {

        List<UserClient> users = this.repository.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(UserClient::getPerson).extracting(Person::getId)
                .containsExactly(UUID.fromString("affb9869-61b3-4100-bafd-df7cf46ef341"),
                        UUID.fromString("e3889817-9be4-4d45-960e-e537bfd01fab"));
        assertThat(users).extracting(UserClient::getBio).containsExactly("Biografia de fulano", "ABCDEFGH 123");
        assertThat(users).extracting(UserClient::getCreationDate).containsOnly(LocalDate.of(2020, 1, 1));
    }

    @Test
    public void delete() {

        List<UserClient> usersListBeforeDelete = this.repository.findAll();

        assertThat(usersListBeforeDelete).hasSize(2);

        this.repository.deleteById(USER_ID);

        List<UserClient> usersListAfterDelete = this.repository.findAll();

        assertThat(usersListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Person person = new Person();
        person.setId(UUID.fromString("6f6abc72-5e5a-4445-b4a5-b90e9e530fdd"));

        UserClient user = new UserClient();
        user.setBio("ABC");
        user.setPerson(person);

        UserClient savedUserClient = this.repository.saveAndFlush(user);

        assertThat(savedUserClient.getId()).isNotNull();
    }

    @Test
    public void update() {

        UserClient userBeforeUpdate = this.repository.findById(USER_ID).get();

        assertThat(userBeforeUpdate.getId()).isEqualTo(USER_ID);
        assertThat(userBeforeUpdate.getPerson().getName()).isEqualTo("Teste da Silva");
        assertThat(userBeforeUpdate.getBio()).isEqualTo("Biografia de fulano");
        assertThat(userBeforeUpdate.getFile().getId()).isEqualTo(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        userBeforeUpdate.setBio("ZZZ");

        UserClient userAfterUpdate = this.repository.saveAndFlush(userBeforeUpdate);

        assertThat(userAfterUpdate.getId()).isEqualTo(USER_ID);
        assertThat(userAfterUpdate.getBio()).isEqualTo("ZZZ");
    }

    @Test
    public void findByPersonId() {

        UserClient user = this.repository.findByPersonId(UUID.fromString("affb9869-61b3-4100-bafd-df7cf46ef341")).get();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(USER_ID);
        assertThat(user.getBio()).isEqualTo("Biografia de fulano");
    }

}
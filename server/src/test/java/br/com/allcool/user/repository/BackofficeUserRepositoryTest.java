package br.com.allcool.user.repository;

import br.com.allcool.person.domain.Person;
import br.com.allcool.test.RepositoryTest;
import br.com.allcool.user.domain.BackofficeUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/person/person.sql", "/sql/user/backofficeuser.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class BackofficeUserRepositoryTest {

    @Autowired
    private BackofficeUserRepository repository;

    private final UUID USER_ID = UUID.fromString("2ae8dc79-9227-4ab3-901f-863123464996");

    @Test
    public void findAll() {

        List<BackofficeUser> users = this.repository.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(BackofficeUser::getPerson).extracting(Person::getId)
                .containsExactly(UUID.fromString("affb9869-61b3-4100-bafd-df7cf46ef341"),
                        UUID.fromString("e3889817-9be4-4d45-960e-e537bfd01fab"));
    }

    @Test
    public void delete() {

        List<BackofficeUser> usersListBeforeDelete = this.repository.findAll();

        assertThat(usersListBeforeDelete).hasSize(2);

        this.repository.deleteById(USER_ID);

        List<BackofficeUser> usersListAfterDelete = this.repository.findAll();

        assertThat(usersListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Person person = new Person();
        person.setId(UUID.fromString("6f6abc72-5e5a-4445-b4a5-b90e9e530fdd"));

        BackofficeUser backofficeUser = new BackofficeUser();
        backofficeUser.setModerator(Boolean.TRUE);
        backofficeUser.setPerson(person);

        BackofficeUser savedBackofficeUser = this.repository.saveAndFlush(backofficeUser);

        assertThat(savedBackofficeUser.getId()).isNotNull();
    }

    @Test
    public void update() {

        BackofficeUser userBeforeUpdate = this.repository.findById(USER_ID).get();

        assertThat(userBeforeUpdate.getId()).isEqualTo(USER_ID);
        assertThat(userBeforeUpdate.getPerson().getName()).isEqualTo("Teste da Silva");
        assertThat(userBeforeUpdate.getModerator()).isTrue();

        userBeforeUpdate.setModerator(Boolean.FALSE);

        BackofficeUser userAfterUpdate = this.repository.saveAndFlush(userBeforeUpdate);

        assertThat(userAfterUpdate.getId()).isEqualTo(USER_ID);
        assertThat(userAfterUpdate.getModerator()).isFalse();
    }
}
package br.com.allcool.person.repository;

import br.com.allcool.person.domain.Person;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/person/person.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private final UUID PERSON_ID = UUID.fromString("affb9869-61b3-4100-bafd-df7cf46ef341");

    @Test
    public void findAll() {

        List<Person> personList = this.repository.findAll();

        assertThat(personList).hasSize(3);
        assertThat(personList).extracting(Person::getName).containsExactly("Teste da Silva", "Mike", "Will");
    }

    @Test
    public void delete() {

        List<Person> personListBeforeDelete = this.repository.findAll();

        assertThat(personListBeforeDelete).hasSize(3);

        this.repository.deleteById(PERSON_ID);

        List<Person> personListAfterDelete = this.repository.findAll();

        assertThat(personListAfterDelete).hasSize(2);
    }

    @Test
    public void save() {

        Person person = new Person();
        person.setName("FooBar");
        person.setBirthDate(LocalDate.of(2020, 1, 1));
        person.setEmail("foobar@gmail.com");
        person.setPassword("senhateste");

        Person savedPerson = this.repository.saveAndFlush(person);

        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(savedPerson.getName()).isEqualTo(person.getName());
        assertThat(savedPerson.getEmail()).isEqualTo(person.getEmail());
    }

    @Test
    public void update() {

        Person personBeforeUpdate = this.repository.findById(PERSON_ID).get();

        assertThat(personBeforeUpdate.getId()).isEqualTo(PERSON_ID);
        assertThat(personBeforeUpdate.getName()).isEqualTo("Teste da Silva");
        assertThat(personBeforeUpdate.getEmail()).isEqualTo("teste@hotmail.com");
        assertThat(personBeforeUpdate.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));

        personBeforeUpdate.setName("Silva");
        personBeforeUpdate.setBirthDate(LocalDate.of(2000, 2, 1));
        personBeforeUpdate.setEmail("silva@hotmail.com");

        Person personAfterUpdate = this.repository.saveAndFlush(personBeforeUpdate);

        assertThat(personAfterUpdate.getId()).isEqualTo(PERSON_ID);
        assertThat(personAfterUpdate.getName()).isEqualTo("Silva");
        assertThat(personAfterUpdate.getEmail()).isEqualTo("silva@hotmail.com");
        assertThat(personAfterUpdate.getBirthDate()).isEqualTo(LocalDate.of(2000, 2, 1));
    }
    
    @Test
    public void findByEmail() {

        Person person = repository.findByEmail("teste@hotmail.com").get();

        assertThat(person.getId()).isEqualTo(PERSON_ID);
        assertThat(person.getName()).isEqualTo("Teste da Silva");
        assertThat(person.getEmail()).isEqualTo("teste@hotmail.com");
        assertThat(person.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
    }
    
    @Test
    public void existsByEmail() {
    	assertTrue(repository.existsByEmail("teste@hotmail.com"));
    	assertFalse(repository.existsByEmail("nao@existe.com"));
    }
}

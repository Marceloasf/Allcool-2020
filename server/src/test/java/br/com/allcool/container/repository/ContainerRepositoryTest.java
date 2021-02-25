package br.com.allcool.container.repository;

import br.com.allcool.container.domain.Container;
import br.com.allcool.enums.ContainerTypeEnum;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/container/container.sql"})
public class ContainerRepositoryTest {

    @Autowired
    private ContainerRepository repository;

    private final UUID CONTAINER_ID = UUID.fromString("f5014a75-c3db-40b8-a49b-2076e1b19801");

    @Test
    public void findAll() {

        List<Container> containerList = this.repository.findAll();

        assertThat(containerList).hasSize(3);
        assertThat(containerList).extracting(Container::getType).containsExactlyInAnyOrder(ContainerTypeEnum.CAN,ContainerTypeEnum.GLASSBOTTLE,ContainerTypeEnum.LONGNECK);
        assertThat(containerList).extracting(Container::getCapacity).containsExactlyInAnyOrder(BigDecimal.valueOf(355L),BigDecimal.valueOf(200L),BigDecimal.valueOf(350L));
    }

    @Test
    public void delete() {

        List<Container> containerListBeforeDelete = this.repository.findAll();

        assertThat(containerListBeforeDelete).hasSize(3);

        this.repository.deleteById(CONTAINER_ID);

        List<Container> containerListAfterDelete = this.repository.findAll();

        assertThat(containerListAfterDelete).hasSize(2);
    }

    @Test
    public void save() {

        Container container = new Container();
        container.setType(ContainerTypeEnum.GLASSBOTTLE);
        container.setCapacity(BigDecimal.valueOf(200L));

        Container savedContainer = this.repository.saveAndFlush(container);

        assertThat(savedContainer.getId()).isNotNull();
        assertThat(savedContainer.getType()).isEqualTo(container.getType());
        assertThat(savedContainer.getCapacity()).isEqualTo(container.getCapacity());
    }

    @Test
    public void update() {

        Container containerBeforeUpdate = this.repository.findById(CONTAINER_ID).get();

        assertThat(containerBeforeUpdate.getId()).isEqualTo(CONTAINER_ID);
        assertThat(containerBeforeUpdate.getType()).isEqualTo(ContainerTypeEnum.LONGNECK);
        assertThat(containerBeforeUpdate.getCapacity()).isEqualTo(BigDecimal.valueOf(355L));

        containerBeforeUpdate.setType(ContainerTypeEnum.GLASSBOTTLE);
        containerBeforeUpdate.setCapacity(BigDecimal.valueOf(200L));

        Container containerAfterUpdate = this.repository.saveAndFlush(containerBeforeUpdate);

        assertThat(containerAfterUpdate.getId()).isEqualTo(CONTAINER_ID);
        assertThat(containerAfterUpdate.getType()).isEqualTo(ContainerTypeEnum.GLASSBOTTLE);
        assertThat(containerAfterUpdate.getCapacity()).isEqualTo(BigDecimal.valueOf(200L));
    }

}
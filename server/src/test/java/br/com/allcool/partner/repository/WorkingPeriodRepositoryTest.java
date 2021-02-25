package br.com.allcool.partner.repository;

import br.com.allcool.partner.domain.Partner;
import br.com.allcool.partner.domain.WorkingPeriod;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@SuppressWarnings("OptionalGetWithoutIsPresent")
@Sql(scripts = {"/sql/address/address.sql", "/sql/file/file.sql", "/sql/brand/brand.sql",
        "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/partner/partner.sql",
        "/sql/partner/partnerproduct.sql", "/sql/partner/workingperiod.sql"})
public class WorkingPeriodRepositoryTest {

    @Autowired
    private WorkingPeriodRepository repository;

    private final UUID WORKINGPERIOD_ID = UUID.fromString("2436a578-a5da-4557-b9e7-0adf4ac687a4");

    @Test
    public void findAll() {

        List<WorkingPeriod> workingPeriodList = this.repository.findAll();

        assertThat(workingPeriodList).hasSize(2);
        assertThat(workingPeriodList).extracting(workingPeriod -> workingPeriod.getPartner().getId())
                .containsExactly(UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720"),
                        UUID.fromString("d0c4238f-a9c9-452b-9c09-315e19d46506"));
        assertThat(workingPeriodList).extracting(WorkingPeriod::getDay)
                .containsExactly("Segunda-feira", "Sábado");
        assertThat(workingPeriodList).extracting(WorkingPeriod::getOpeningTime)
                .containsExactly(LocalTime.of(17, 00, 00),
                        LocalTime.of(17, 30, 00));
        assertThat(workingPeriodList).extracting(WorkingPeriod::getClosingTime)
                .containsExactly(LocalTime.of(23, 00, 00),
                        LocalTime.of(00, 30, 00));
    }

    @Test
    public void delete() {

        List<WorkingPeriod> workingPeriodListBeforeDelete = this.repository.findAll();

        assertThat(workingPeriodListBeforeDelete).hasSize(2);

        this.repository.deleteById(WORKINGPERIOD_ID);

        List<WorkingPeriod> workingPeriodListAfterDelete = this.repository.findAll();

        assertThat(workingPeriodListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Partner partner = new Partner();
        partner.setId(UUID.fromString("68a0e950-37b8-4f01-9f40-f33fdfbc8720"));
        partner.setName("Teste parceiro");

        WorkingPeriod workingPeriod = new WorkingPeriod();
        workingPeriod.setPartner(partner);
        workingPeriod.setDay("Terça-feira");
        workingPeriod.setOpeningTime(LocalTime.of(17, 00, 00));
        workingPeriod.setClosingTime(LocalTime.of(23, 00, 00));

        WorkingPeriod savedWorkingPeriod = this.repository.saveAndFlush(workingPeriod);

        assertThat(savedWorkingPeriod.getId()).isNotNull();
        assertThat(savedWorkingPeriod.getPartner()).isEqualTo(workingPeriod.getPartner());
        assertThat(savedWorkingPeriod.getDay()).isEqualTo(workingPeriod.getDay());
        assertThat(savedWorkingPeriod.getOpeningTime()).isEqualTo(workingPeriod.getOpeningTime());
        assertThat(savedWorkingPeriod.getClosingTime()).isEqualTo(workingPeriod.getClosingTime());
    }

    @Test
    public void update() {

        WorkingPeriod workingPeriodBeforeUpdate = this.repository.findById(WORKINGPERIOD_ID).get();

        assertThat(workingPeriodBeforeUpdate.getId()).isEqualTo(WORKINGPERIOD_ID);
        assertThat(workingPeriodBeforeUpdate.getDay()).isEqualTo("Segunda-feira");
        assertThat(workingPeriodBeforeUpdate.getOpeningTime()).isEqualTo(LocalTime.of(17, 00, 00));
        assertThat(workingPeriodBeforeUpdate.getClosingTime()).isEqualTo(LocalTime.of(23, 00, 00));

        workingPeriodBeforeUpdate.setDay("Domingo");
        workingPeriodBeforeUpdate.setOpeningTime(LocalTime.of(18, 10, 00));
        workingPeriodBeforeUpdate.setClosingTime(LocalTime.of(23, 40, 00));

        WorkingPeriod workingPeriodAfterupdate = this.repository.saveAndFlush(workingPeriodBeforeUpdate);

        assertThat(workingPeriodAfterupdate.getId()).isEqualTo(WORKINGPERIOD_ID);
        assertThat(workingPeriodAfterupdate.getDay()).isEqualTo("Domingo");
        assertThat(workingPeriodAfterupdate.getOpeningTime()).isEqualTo(LocalTime.of(18, 10, 00));
        assertThat(workingPeriodAfterupdate.getClosingTime()).isEqualTo(LocalTime.of(23, 40, 00));
    }
}

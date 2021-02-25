package br.com.allcool.user.repository;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.test.RepositoryTest;
import br.com.allcool.user.domain.UserClient;
import br.com.allcool.user.domain.UserClientAchievement;
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
@Sql(scripts = {"/sql/file/file.sql", "/sql/producttype/producttype.sql", "/sql/brand/brand.sql",
        "/sql/product/product.sql", "/sql/person/person.sql", "/sql/user/userclient.sql",
        "/sql/achievement/achievement.sql", "/sql/user/userclient-achievement.sql"
})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class UserClientAchievementRepositoryTest {

    @Autowired
    private UserClientAchievementRepository repository;

    private final UUID USERACHIEVEMENT_ID = UUID.fromString("f455b488-aaf6-4968-9d71-fcdf7d690397");

    @Test
    public void findAll() {

        List<UserClientAchievement> userAchievements = this.repository.findAll();

        assertThat(userAchievements).hasSize(2);
        assertThat(userAchievements).extracting(userAchievementClientAchievement -> userAchievementClientAchievement.getAchievement().getId())
                .containsExactly(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"),
                        UUID.fromString("fdb671cc-9b08-11ea-bb37-0242ac130002"));
        assertThat(userAchievements).extracting(userAchievementClientAchievement -> userAchievementClientAchievement.getUser().getId())
                .containsExactly(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"),
                        UUID.fromString("7db7d491-8f37-4bd6-a061-e865f9df4b6a"));
        assertThat(userAchievements).extracting(UserClientAchievement::getLevel).containsExactly(1L, 5L);
        assertThat(userAchievements).extracting(userClientAchievement -> userClientAchievement.getProgress().toString())
                .containsExactly("10.00", "50.00");
    }

    @Test
    public void delete() {

        List<UserClientAchievement> userAchievementsListBeforeDelete = this.repository.findAll();

        assertThat(userAchievementsListBeforeDelete).hasSize(2);

        this.repository.deleteById(USERACHIEVEMENT_ID);

        List<UserClientAchievement> userAchievementsListAfterDelete = this.repository.findAll();

        assertThat(userAchievementsListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Achievement achievement = new Achievement();
        achievement.setId(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));

        UserClient userClient = new UserClient();
        userClient.setId(UUID.fromString("3793d49a-01fa-47cc-b762-da24738b750c"));

        UserClientAchievement userAchievement = new UserClientAchievement();
        userAchievement.setLevel(100L);
        userAchievement.setProgress(BigDecimal.TEN);
        userAchievement.setAchievement(achievement);
        userAchievement.setUser(userClient);

        UserClientAchievement savedUserClientAchievement = this.repository.saveAndFlush(userAchievement);

        assertThat(savedUserClientAchievement.getId()).isNotNull();
    }

    @Test
    public void update() {

        UserClientAchievement userAchievementBeforeUpdate = this.repository.findById(USERACHIEVEMENT_ID).get();

        assertThat(userAchievementBeforeUpdate.getId()).isEqualTo(USERACHIEVEMENT_ID);
        assertThat(userAchievementBeforeUpdate.getLevel()).isEqualTo(1L);
        assertThat(userAchievementBeforeUpdate.getProgress().toString()).isEqualTo("10.00");
        assertThat(userAchievementBeforeUpdate.getAchievement().getId()).isEqualTo(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));

        userAchievementBeforeUpdate.setLevel(8L);
        userAchievementBeforeUpdate.setProgress(BigDecimal.ZERO);

        UserClientAchievement userAchievementAfterUpdate = this.repository.saveAndFlush(userAchievementBeforeUpdate);

        assertThat(userAchievementAfterUpdate.getId()).isEqualTo(USERACHIEVEMENT_ID);
        assertThat(userAchievementAfterUpdate.getLevel()).isEqualTo(8L);
        assertThat(userAchievementAfterUpdate.getProgress()).isEqualTo(BigDecimal.ZERO);
    }
}
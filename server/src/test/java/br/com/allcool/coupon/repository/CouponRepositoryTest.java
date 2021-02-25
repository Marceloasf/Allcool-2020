package br.com.allcool.coupon.repository;

import br.com.allcool.achievement.domain.Achievement;
import br.com.allcool.coupon.domain.Coupon;
import br.com.allcool.enums.CouponTypeEnum;
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
@Sql(scripts = {"/sql/file/file.sql", "/sql/brand/brand.sql", "/sql/producttype/producttype.sql", "/sql/product/product.sql", "/sql/achievement/achievement.sql", "/sql/coupon/coupon.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class CouponRepositoryTest {

    @Autowired
    private CouponRepository repository;

    private final UUID COUPON_ID = UUID.fromString("7ce659c9-803b-44d8-a770-641980caf688");

    @Test
    public void findAll() {

        List<Coupon> couponList = this.repository.findAll();

        assertThat(couponList).hasSize(2);
        assertThat(couponList).extracting(coupon -> coupon.getAchievement().getId()).containsExactly(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"), UUID.fromString("fdb671cc-9b08-11ea-bb37-0242ac130002"));
        assertThat(couponList).extracting(coupon -> coupon.getValue().toString()).containsExactly("20.00", "15.50");
        assertThat(couponList).extracting(Coupon::getDescription).containsExactly("Desconto de 20 reais", "Desconto de 15 reais");
        assertThat(couponList).extracting(Coupon::getLevel).containsExactly(5L, 3L);
        assertThat(couponList).extracting(Coupon::getType).containsExactly(CouponTypeEnum.CURRENCY, CouponTypeEnum.PERCENTAGE);
    }

    @Test
    public void delete() {

        List<Coupon> CouponListBeforeDelete = this.repository.findAll();

        assertThat(CouponListBeforeDelete).hasSize(2);

        this.repository.deleteById(COUPON_ID);

        List<Coupon> couponListAfterDelete = this.repository.findAll();

        assertThat(couponListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Achievement achievement = new Achievement();
        achievement.setId(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));

        Coupon coupon = new Coupon();
        coupon.setAchievement(achievement);
        coupon.setValue(BigDecimal.valueOf(5.00));
        coupon.setDescription("Desconto de 5 reais");
        coupon.setLevel(1L);
        coupon.setType(CouponTypeEnum.CURRENCY);

        Coupon savedCoupon = this.repository.saveAndFlush(coupon);

        assertThat(savedCoupon.getId()).isNotNull();
        assertThat(savedCoupon.getAchievement()).isEqualTo(coupon.getAchievement());
        assertThat(savedCoupon.getValue()).isEqualTo(coupon.getValue());
        assertThat(savedCoupon.getDescription()).isEqualTo(coupon.getDescription());
        assertThat(savedCoupon.getLevel()).isEqualTo(coupon.getLevel());
        assertThat(savedCoupon.getType()).isEqualTo(coupon.getType());
    }

    @Test
    public void update() {

        Achievement achievement = new Achievement();
        achievement.setId(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));

        Coupon couponBeforeUpdate = this.repository.findById(COUPON_ID).get();

        assertThat(couponBeforeUpdate.getId()).isEqualTo(COUPON_ID);
        assertThat(couponBeforeUpdate.getAchievement().getId()).isEqualTo(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));
        assertThat(couponBeforeUpdate.getValue().toString()).isEqualTo("20.00");
        assertThat(couponBeforeUpdate.getDescription()).isEqualTo("Desconto de 20 reais");
        assertThat(couponBeforeUpdate.getLevel()).isEqualTo(5L);
        assertThat(couponBeforeUpdate.getType()).isEqualTo(CouponTypeEnum.CURRENCY);

        couponBeforeUpdate.setValue(BigDecimal.valueOf(25.51));
        couponBeforeUpdate.setDescription("Desconto de 25.50%");
        couponBeforeUpdate.setLevel(6L);
        couponBeforeUpdate.setType(CouponTypeEnum.PERCENTAGE);

        Coupon couponAfterUpdate = this.repository.saveAndFlush(couponBeforeUpdate);

        assertThat(couponAfterUpdate.getId()).isEqualTo(COUPON_ID);
        assertThat(couponAfterUpdate.getAchievement().getId()).isEqualTo(UUID.fromString("ce583254-d732-4fe7-85fb-a11585399766"));
        assertThat(couponAfterUpdate.getValue().toString()).isEqualTo("25.51");
        assertThat(couponAfterUpdate.getDescription()).isEqualTo("Desconto de 25.50%");
        assertThat(couponAfterUpdate.getLevel()).isEqualTo(6L);
        assertThat(couponAfterUpdate.getType()).isEqualTo(CouponTypeEnum.PERCENTAGE);

    }
}
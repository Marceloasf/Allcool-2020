package br.com.allcool.converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanToYesOrNoTest {

    private final BooleanToYesOrNo booleanToYesOrNo = new BooleanToYesOrNo();

    @Test
    public void convertToDatabaseColumn() {

        String shouldBeY = booleanToYesOrNo.convertToDatabaseColumn(Boolean.TRUE);

        assertThat(shouldBeY).isEqualTo("Y");

        String shouldBeN = booleanToYesOrNo.convertToDatabaseColumn(Boolean.FALSE);

        assertThat(shouldBeN).isEqualTo("N");
    }

    @Test
    public void convertToEntityAttribute() {

        Boolean shouldBeTrue = booleanToYesOrNo.convertToEntityAttribute("Y");

        assertThat(shouldBeTrue).isTrue();

        Boolean shouldBeFalse = booleanToYesOrNo.convertToEntityAttribute("N");

        assertThat(shouldBeFalse).isFalse();
    }
}
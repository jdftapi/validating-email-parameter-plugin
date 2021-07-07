package io.jenkins.plugins.validation.emailparameter;

import io.jenkins.plugins.util.MockData;
import io.jenkins.plugins.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class EmailValidationTest {

    private ValidatingEmailParameterDefinition definition;

    @Before
    public void setUp() {

        definition = new ValidatingEmailParameterDefinition(MockData.NAME, MockData.DEFAULT_VALUE,
                MockData.DOMAIN, MockData.DESCRIPTION, MockData.EXTERNAL_EMAIL);
    }

    @Test
    public void emailValidationTest() {

        String params = ValidationUtils
                .definitionParameter(definition.getDefaultValue(), definition.getDomain());

        Arrays.stream(params.split(" ")).parallel().forEach(param -> {

            assertThat(accept(param)).isTrue();
            log.info("Valid email address : {}", param);


        });


    }

    private boolean accept(String param) {

        return EmailValidator.getInstance().isValid(param);

    }

}

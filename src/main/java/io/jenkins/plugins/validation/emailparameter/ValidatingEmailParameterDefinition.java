package io.jenkins.plugins.validation.emailparameter;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import io.jenkins.plugins.constant.PluginInfo;
import io.jenkins.plugins.util.ValidationUtils;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;

public class ValidatingEmailParameterDefinition extends ParameterDefinition
{

    private String defaultValue;
    private String domain;
    private boolean externalEmail;

    public ValidatingEmailParameterDefinition(String name, String defaultValue, String domain,
                                              Boolean externalEmail)
    {
        this(name, defaultValue, domain, null, externalEmail);
    }

    @DataBoundConstructor
    public ValidatingEmailParameterDefinition(String name, String defaultValue, String domain,
                                              String description, Boolean externalEmail)
    {
        super(name, description);

        this.defaultValue = ValidationUtils.definitionParameter(defaultValue, domain);
        this.domain = domain;
        this.externalEmail = externalEmail;

    }

    @CheckForNull
    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest, JSONObject jsonObject)
    {

        JSONObject valid = ValidationUtils.buildParameter(domain, externalEmail, jsonObject);

        ValidatingEmailParameterValue parameterValue = staplerRequest
                .bindJSON
                        (
                                ValidatingEmailParameterValue.class,
                                valid
                        );

        parameterValue.setDescription(getDescription());
        parameterValue.setValid(
                valid.getBoolean(ValidationUtils.Key.IS_VALID.getValue())
        );

        if (valid.has(ValidationUtils.Key.ERROR.getValue()))
        {
            parameterValue.setError(
                    valid.getString(ValidationUtils.Key.ERROR.getValue())
            );
        }


        return parameterValue;
    }

    @CheckForNull
    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest)
    {
        return null;
    }

    @Override
    public ValidatingEmailParameterValue getDefaultParameterValue()
    {
        return new ValidatingEmailParameterValue(getName(), getDefaultValue(), getDescription());
    }

    @Extension
    @Symbol("validation")
    public static class DescriptorImpl extends ParameterDescriptor
    {

        @Override
        public String getDisplayName()
        {
            return PluginInfo.DISPLAY_NAME;
        }

        @Override
        public String getHelpFile()
        {
            return PluginInfo.HELP_FILE;
        }

        public FormValidation doValidate(@QueryParameter("domain") final String domain,
                                         @QueryParameter("value") final String value,
                                         @QueryParameter("externalEmail") final Boolean externalEmail)
        {

            return ValidationUtils.formParameter(domain, value, externalEmail);

        }
    }


    public String getDefaultValue()
    {
        return defaultValue;
    }

    public String getDomain()
    {
        return domain;
    }

    /**
     * @return externalEmail
     */
    public Boolean getExternalEmail()
    {
        return externalEmail;
    }


}

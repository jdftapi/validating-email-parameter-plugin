package io.jenkins.plugins.validation.emailparameter;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.StringParameterValue;
import hudson.tasks.BuildWrapper;
import io.jenkins.plugins.constant.ErrorCode;
import io.jenkins.plugins.exception.ValidationException;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

public class ValidatingEmailParameterValue extends StringParameterValue
{

    /**
     * email is valid
     */
    private Boolean isValid;

    /**
     * error message
     */
    private String error;

    @DataBoundConstructor
    public ValidatingEmailParameterValue(String name, String value)
    {
        this(name, value, null);
    }

    public ValidatingEmailParameterValue(String name, String value, String description)
    {
        super(name, value, description);
    }

    @Override
    public BuildWrapper createBuildWrapper(AbstractBuild<?, ?> build)
    {

        if (!isValid)
        {
            return new BuildWrapper()
            {
                @Override
                public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener)
                        throws IOException
                {
                    throw new ValidationException(ErrorCode.INVALID_EMAIL, getError());
                }
            };
        }
        else
        {
            return null;
        }
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public Boolean getValid()
    {
        return isValid;
    }

    public void setValid(Boolean valid)
    {
        isValid = valid;
    }

    @Override
    public int hashCode()
    {
        final int prime = 71;

        int result = super.hashCode();

        result = prime * result;

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }

        ValidatingEmailParameterValue other = (ValidatingEmailParameterValue) obj;

        if (value == null)
        {
            if (other.value != null)
            {
                return false;
            }
        }
        else if (!value.equals(other.value))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "(ValidatingEmailParameterValue) " + getName() + "='" + getValue() + "'";
    }
}

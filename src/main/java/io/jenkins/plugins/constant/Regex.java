package io.jenkins.plugins.constant;

public abstract class Regex
{

    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    public abstract static class Domain
    {

        public static final String DOMAIN_PATTERN = "^[A-Za-z0-9._%+-]+@{DOMAIN_NAME}";

        public static final String DOMAIN_NAME = "{DOMAIN_NAME}";

    }

}

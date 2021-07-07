package io.jenkins.plugins.util;

import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;

public abstract class ValidationUtils {

    private static StringBuilder builder;
    private static String normalizeSpace;
    private static List<String> params;
    /*private static Map<String,String> paramMap;*/

    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    private static boolean isMailAddressInDomain(String emailAddress, String domain) {
        String[] addressParts = emailAddress.split("@");
        return addressParts[addressParts.length - 1].equals(domain);
    }

    public static JSONObject buildParameter(String domain, Boolean externalEmail,
                                            JSONObject jsonObject) {

        builder = new StringBuilder();
        normalizeSpace = jsonObject.getString(Key.VALUE.getValue()).trim();
        params = Arrays.asList(normalizeSpace.split(" "));

        if (!params.isEmpty()) {
            for (String s : params) {
                if (emailValidator.isValid(s)) {
                    if (!externalEmail) {
                        if (isMailAddressInDomain(s, domain)) {
                            builder.append(s).append(" ");
                        } else {
                            jsonObject.put(Key.IS_VALID.getValue(), false);
                            jsonObject.put(Key.ERROR.getValue(),
                                    "Invalid email for parameter [" + jsonObject.getString("name")
                                            + "] expected domain : @" + domain + " specified: " + s);
                            return jsonObject;
                        }
                    } else {
                        builder.append(s).append(" ");
                    }
                } else if (!s.isEmpty()) {
                    if (emailValidator.isValid(s + "@" + domain)) {
                        builder.append(s).append("@").append(domain).append(" ");
                    } else {
                        jsonObject.put(Key.IS_VALID.getValue(), false);
                        if(!externalEmail) {
                            jsonObject.put(Key.ERROR.getValue(),
                               "Invalid email for parameter [" + jsonObject.getString("name")
                                 + "] expected domain : @" + domain + " specified: " + s);
                        } else {
                            jsonObject.put(Key.ERROR.getValue(),
                                "Invalid email for parameter [" + jsonObject.getString("name")
                                   + "] Value entered does not match "
                                   + "email pattern; specified: " + s);
                        }
                        return jsonObject;
                    }
                } else {
                    jsonObject.put(Key.IS_VALID.getValue(), false);
                    jsonObject.put(Key.ERROR.getValue(),
                        "Invalid email for parameter [ " + jsonObject.getString("name")
                            + " ] Expected domain : @" + domain
                            + " Specified: [ Email is Empty! ]");
                    return jsonObject;
                }
            }
            jsonObject.replace(Key.VALUE.getValue(), String.valueOf(builder).trim());
        }
        jsonObject.put(Key.IS_VALID.getValue(), true);
        return jsonObject;
    }

    public static String definitionParameter(String defaultValue, String domain) {

        builder = new StringBuilder();
        normalizeSpace = defaultValue.trim();
        params = Arrays.asList(normalizeSpace.split(" "));

        if (!params.isEmpty()) {
            for (String value : params) {
                if (emailValidator.isValid(value)) {
                    builder.append(value).append(" ");
                } else if (!value.isEmpty()) {
                    builder.append(value).append("@").append(domain).append(" ");
                } else {
                    return defaultValue;
                }
            }
            return String.valueOf(builder).trim();
        } else {
            return defaultValue;
        }
    }

    public static FormValidation formParameter(String domain, String value, Boolean externalEmail) {
        builder = new StringBuilder();
        normalizeSpace = value.trim();
        params = Arrays.asList(normalizeSpace.split(" "));

        if (!params.isEmpty()) {
            for (String s : params) {
                if (emailValidator.isValid(s)) {
                    if (!externalEmail) {
                        if (isMailAddressInDomain(s, domain)) {
                            builder.append(s).append(" ");
                        } else {
                            return FormValidation.error("Value entered does not match domain : " + s);
                        }
                    } else {
                        builder.append(s).append(" ");
                    }
                } else if (!s.isEmpty()) {
                    if (emailValidator.isValid(s + "@" + domain)) {
                        builder.append(s).append("@").append(domain).append(" ");
                    } else {
                        if (!externalEmail) {
                            return FormValidation.error("Value entered does not match domain : " + s);
                        } else {
                            return FormValidation.error(
                                "Value entered does not match email address pattern : " + s);
                        }
                    }
                } else {
                    return FormValidation.warning("Email address is empty.");
                }
            }
            return FormValidation.respond(FormValidation.Kind.OK,
                    "<div class='ok' style='color:green;'><span>&#9989;</span><b> Valid Email : </b>"
                            + builder + "</div>");
        } else {
            return FormValidation.respond(FormValidation.Kind.OK,
                    "<div class='ok' style='color:green;'><span>&#9989;</span><b> Valid Email : </b>" + value
                            + "</div>");
        }
    }

    public enum Key {
        ERROR("error"),
        VALUE("value"),
        IS_VALID("is_valid");

        private final String value;

        Key(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}

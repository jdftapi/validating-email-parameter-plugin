package io.jenkins.plugins.exception;

import java.util.Objects;

public class ValidationException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    /**
     * {@link io.jenkins.plugins.constant.ErrorCode}.
     */
    private int errorCode;

    /**
     * error description.
     */
    private String errorDescription;

    /**
     * @param cause {@link Throwable}
     */
    public ValidationException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message message
     */
    public ValidationException(final String message)
    {
        super(message);
    }

    /**
     * @param code    {@link io.jenkins.plugins.constant.ErrorCode}
     * @param message message
     */
    public ValidationException(final int code, final String message)
    {
        super(buildMessage(code, null, message));
        this.errorCode = code;
    }

    /**
     * @param code    {@link io.jenkins.plugins.constant.ErrorCode}
     * @param message message
     * @param cause   {@link Throwable}
     */
    public ValidationException(final int code, final String message,
                               final Throwable cause)
    {
        super(buildMessage(code, null, message), cause);
        this.errorCode = code;
    }

    /**
     * @param code             {@link io.jenkins.plugins.constant.ErrorCode}
     * @param errorDescription error description
     * @param message          message
     */
    public ValidationException(final int code,
                               final String errorDescription, final String message)
    {
        super(buildMessage(code, errorDescription, message));
        this.errorCode = code;
        this.errorDescription = errorDescription;
    }

    /**
     * @param code             {@link io.jenkins.plugins.constant.ErrorCode}
     * @param errorDescription error description
     * @param message          msg
     * @param cause            Throwable
     */
    public ValidationException(final int code, final String errorDescription,
                               final String message, final Throwable cause)
    {
        super(buildMessage(code, errorDescription, message), cause);
        this.errorCode = code;
        this.errorDescription = errorDescription;
    }

    /**
     * @param code             {@link io.jenkins.plugins.constant.ErrorCode}
     * @param errorDescription error description
     * @param message          msg
     * @return String
     */

    private static String buildMessage(final int code,
                                       final String errorDescription, final String message)
    {
        return message + " (errorCode: " + code + (Objects.nonNull(errorDescription) ? "-"
                + errorDescription : "") + ")";
    }

    /**
     * @param errorCode ErrorCode
     */
    public void setErrorCode(final int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * @return ErrorCode
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * @return java.lang
     */
    public String getErrorDescription()
    {
        return errorDescription;
    }

    /**
     * @param errorDescription error description
     */
    public void setErrorDescription(final String errorDescription)
    {
        this.errorDescription = errorDescription;
    }
}

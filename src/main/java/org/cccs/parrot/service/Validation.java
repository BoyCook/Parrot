package org.cccs.parrot.service;

import org.apache.commons.lang.Validate;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * User: boycook
 * Date: 09/07/2012
 * Time: 12:53
 */
public final class Validation {

    private static final ValidatorFactory validatorFactory = javax.validation.Validation.buildDefaultValidatorFactory();
    private static final int INITIAL_SIZE = 1024;

    private Validation() {}

    public static void validate(int value) {
        validate(value, "You must specify a valid integer value");
    }

    public static void validate(int value, final String message) {
        if (value <=0) {
            throw  new IllegalArgumentException(message);
        }
    }

    public static void validate(final Object entity) {
        Validate.notNull(entity, "Entity must not be null");
        Set<ConstraintViolation<Object>> violations = validatorFactory.getValidator().validate(entity);
        if (!violations.isEmpty()) {
            throw validationFailure(violations);
        }
    }

    public static <T> ValidationException validationFailure(final Set<ConstraintViolation<T>> violations) {
        final StringBuffer buffer = new StringBuffer(INITIAL_SIZE);
        for (ConstraintViolation<T> violation : violations) {
            buffer.append("Validation failed for path [")
                    .append(violation.getPropertyPath())
                    .append("]: ")
                    .append(violation.getMessage())
                    .append(" - value: [")
                    .append(violation.getInvalidValue())
                    .append("]")
                    .append("\n");
        }
        return new ValidationException(buffer.toString());
    }

    public static <T extends Throwable> T findNestedException(final Throwable e, final Class<T> c) {
        if (e.getCause() != null) {
            if (e.getCause().getClass().equals(c)) {
                return (T) e.getCause();
            } else {
                return findNestedException(e.getCause(), c);
            }
        }
        return null;
    }
}

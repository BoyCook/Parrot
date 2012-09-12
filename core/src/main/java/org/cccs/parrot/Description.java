package org.cccs.parrot;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 13:28
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface Description {

    /**
     * This is the friendly name which will be used in the UI
     */
    String value();
}

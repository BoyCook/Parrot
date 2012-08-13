package org.cccs.parrot.service;

import org.junit.Test;

/**
 * User: boycook
 * Date: 13/08/2012
 * Time: 16:27
 */
public class TestValidation {

    @Test(expected = IllegalArgumentException.class)
    public void validateShouldWork() {
        Validation.validate(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateWithMessageShouldWork() {
        Validation.validate(0, "Message");
    }
}

package org.cccs.parrot.web;

/**
 * User: boycook
 * Date: 09/07/2012
 * Time: 16:49
 */
public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

package org.cccs.parrot.util;

/**
 * User: boycook
 * Date: 19/07/2012
 * Time: 17:00
 */
public class SomeObject {

    private String name;

    public String getName() {
        throw new RuntimeException("");
    }

    public void setName(String name) {
        this.name = name;
    }
}

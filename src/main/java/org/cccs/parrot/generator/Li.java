package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:38
 */
public class Li extends DOMElement {

    public Li() {
        super("Li", "li");
    }

    public Li(String value) {
        super("Li", "li");
        setValue(value);
    }
}

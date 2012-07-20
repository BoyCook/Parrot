package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:10
 */
public class Div extends DOMElement {

    public Div() {
        super("Div", "div");
    }

    public Div(String value) {
        super("Div", "div");
        setValue(value);
    }
}

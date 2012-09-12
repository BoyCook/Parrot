package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:10
 */
public class Div extends DOMElement {

    public Div() {
        this(null);
    }

    public Div(String value) {
        this(value, null);
    }

    public Div(String value, String id) {
        super("Div", "div");
        setValue(value);
        setId(id);
    }
}

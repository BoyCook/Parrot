package org.cccs.parrot.generator;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:38
 */
public class A extends DOMElement {

    private final String href;

    public A() {
        super("Anchor", "a");
        this.href = null;
    }

    public A(String value, String href) {
        super("Anchor", "a");
        this.href = href;
        setValue(value);
    }

    public String getHref() {
        return href;
    }

    @Override
    public String getStartTag() {
        return format("<%s href=\"%s\">", getTagName(), getHref());
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append(getStartTag());
        value.append(getValue());
        value.append(getEndTag());
        return value.toString();
    }
}

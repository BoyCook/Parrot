package org.cccs.parrot.generator;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:38
 */
public class A extends DOMElement {

    private final String href;
    private final String target;

    public A(String value, String href) {
        this(value, href, null);
    }

    public A(String value, String href, String target) {
        super("Anchor", "a");
        this.href = href;
        this.target = target;
        setValue(value);
    }

    public String getHref() {
        return href;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String getStartTag() {
        if (getTarget() != null) {
            return format("<%s href=\"%s\" target=\"%s\">", getTagName(), getHref(), getTarget());
        } else {
            return format("<%s href=\"%s\">", getTagName(), getHref());
        }
    }
}

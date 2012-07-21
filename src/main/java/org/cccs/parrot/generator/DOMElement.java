package org.cccs.parrot.generator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:00
 */
public abstract class DOMElement {

    private final String name;
    private final String tagName;
    private String id;
    private String value;

    private final List<DOMElement> children;

    public DOMElement(String name, String tagName) {
        this.name = name;
        this.tagName = tagName;
        this.children = new ArrayList<DOMElement>();
    }

    public String getName() {
        return name;
    }

    public String getTagName() {
        return tagName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTag() {
        if (getId() != null) {
            return getStartTag(getTagName(), getId());
        } else {
            return getStartTag(getTagName());
        }
    }

    public String getEndTag() {
        return getEndTag(getTagName());
    }

    public DOMElement append(DOMElement element) {
        this.children.add(element);
        return this;
    }

    public List<DOMElement> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append(getStartTag());

        if (getValue() != null) {
            value.append(getValue());
        } else {
            for (DOMElement child : getChildren()) {
                value.append(child.toString());
            }
        }

        value.append(getEndTag());
        return value.toString();
    }

    public static String getStartTag(String tag) {
        return format("<%s>", tag);
    }

    public static String getStartTag(String tag, String id) {
        return format("<%s id=\"%s\">", tag, id);
    }

    public static String getEndTag(String tag) {
        return format("</%s>", tag);
    }
}

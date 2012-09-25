package org.cccs.parrot.domain;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 13:53
 */
public class Attribute extends DescribedEntity {

    private boolean identity;
    private boolean systemManaged;
    private boolean editable;
    private boolean column;

    public Attribute(String name, String description, Class clazz) {
        super(name, description, clazz);
        setEditable(true);
    }

    public boolean isSystemManaged() {
        return systemManaged;
    }

    public void setSystemManaged(boolean systemManaged) {
        this.systemManaged = systemManaged;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }
}

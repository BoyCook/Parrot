package org.cccs.parrot.domain;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 13:53
 */
public class Attribute extends DescribedEntity {

    private boolean systemManaged;

    public Attribute(String name, String description, Class clazz, boolean systemManaged) {
        super(name, description, clazz);
        this.systemManaged = systemManaged;
    }

    public Attribute(String name, String description, Class clazz) {
        this(name, description, clazz, false);
    }

    public boolean isSystemManaged() {
        return systemManaged;
    }

    public void setSystemManaged(boolean systemManaged) {
        this.systemManaged = systemManaged;
    }
}

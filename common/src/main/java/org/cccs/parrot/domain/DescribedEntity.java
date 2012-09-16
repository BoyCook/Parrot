package org.cccs.parrot.domain;

/**
 * For lack of a better name - it's a wrapper around a class
 *
 * User: boycook
 * Date: 30/06/2012
 * Time: 16:53
 */
public abstract class DescribedEntity {

    private final String name;
    private final String description;
    private final Class clazz;

    public DescribedEntity(String name, String description, Class clazz) {
        this.name = name;
        this.description = description;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Class getClazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DescribedEntity that = (DescribedEntity) o;

        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null){
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }
}

package org.cccs.parrot.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 13:48
 */
public class Entity extends DescribedEntity {

    private final Set<Attribute> attributes;
    private final Set<String> paths;

    public Entity(String name, String description, Class clazz, Set<Attribute> attributes, Set<String> paths) {
        super(name, description, clazz);
        this.paths = paths;
        this.attributes = attributes;
    }

    public Entity(String name, String description, Class clazz) {
        this(name, description, clazz, new HashSet<Attribute>(), new HashSet<String>());
    }

    public Set<String> getPaths() {
        return paths;
    }

    public void addPath(String path) {
        this.paths.add(path);
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }
}

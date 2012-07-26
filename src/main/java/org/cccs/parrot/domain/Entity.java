package org.cccs.parrot.domain;

import java.util.*;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 13:48
 */
public class Entity extends DescribedEntity {

    private final Map<String, Attribute> attributes;
    private final Set<String> paths;

    public Entity(String name, String description, Class clazz, Map<String, Attribute> attributes, Set<String> paths) {
        super(name, description, clazz);
        this.paths = paths;
        this.attributes = attributes;
    }

    public Entity(String name, String description, Class clazz) {
        this(name, description, clazz, new TreeMap<String, Attribute>(new PropertyComparator()), new HashSet<String>());
    }

    public Set<String> getPaths() {
        return paths;
    }

    public void addPath(String path) {
        this.paths.add(path);
    }

    public Collection<Attribute> getAttributes() {
        return attributes.values();
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.put(attribute.getName(), attribute);
    }

    public Attribute getAttribute(String name) {
        return this.attributes.get(name);
    }
}

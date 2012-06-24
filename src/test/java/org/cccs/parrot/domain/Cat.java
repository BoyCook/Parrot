package org.cccs.parrot.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 17:08
 */
@Entity
@Table
public class Cat {

    private Long id;
    private String name;
    private Person owner;
    private Set<Country> countries;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }

    public Cat(final String name, final Person owner) {
        this.name = name;
        this.owner = owner;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Person getOwner() {
        return owner;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cat_countries", joinColumns = {@JoinColumn(name = "cat_id")}, inverseJoinColumns = @JoinColumn(name = "cntId"))
    public Set<Country> getCountries() {
        return countries;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOwner(final Person owner) {
        this.owner = owner;
    }

    public void setCountries(final Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cat cat = (Cat) o;

        if (!id.equals(cat.id)) return false;
        if (!name.equals(cat.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

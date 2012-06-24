package org.cccs.parrot.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * User: boycook
 * Date: 12/07/2011
 * Time: 15:53
 */
@Entity
@Table(name = "country")
public class Country {

    private long id;
    private String name;
    private Set<Dog> dogs;
    private Set<Cat> cats;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cat_countries", joinColumns = {@JoinColumn(name = "cntId")}, inverseJoinColumns = @JoinColumn(name = "cat_id"))
    public Set<Cat> getCats() {
        return cats;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dog_countries", joinColumns = {@JoinColumn(name = "cntId")}, inverseJoinColumns = @JoinColumn(name = "dog_id"))
    public Set<Dog> getDogs() {
        return dogs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id && name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}

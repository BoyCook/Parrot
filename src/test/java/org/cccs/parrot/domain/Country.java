package org.cccs.parrot.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: boycook
 * Date: 12/07/2011
 * Time: 15:53
 */
@Entity
@Table(name = "country")
public class Country {

    private Long id;
    private String name;
    private Set<Dog> dogs;
    private Set<Cat> cats;

    public Country() {
        this(null);
    }

    public Country(String name) {
        this.name = name;
        this.dogs = new HashSet<Dog>();
        this.cats = new HashSet<Cat>();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
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

    public void setId(Long id) {
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
}

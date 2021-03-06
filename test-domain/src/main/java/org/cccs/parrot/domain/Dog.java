package org.cccs.parrot.domain;

import org.cccs.parrot.Description;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 17:08
 */
@Entity
@Table
public class Dog {

    private Long id;
    private String name;
    private Person owner;
    private Set<Country> countries;

    public Dog() {
        this(null, null);
    }

    public Dog(String name) {
        this(name, null);
    }

    public Dog(String name, Person owner) {
        this.name = name;
        this.owner = owner;
        this.countries = new HashSet<Country>();
    }

    @Description("ID")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Description("Name")
    @Column(unique = true, name = "name")
    public String getName() {
        return name;
    }

    @NotNull(message = "Owner must be specified")
    @ForeignKey(name = "FK_DOG_OWNER")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Person getOwner() {
        return owner;
    }

    @Description("Countries")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dog_countries", joinColumns = {@JoinColumn(name = "dog_id")}, inverseJoinColumns = @JoinColumn(name = "cntId"))
    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}

package org.cccs.parrot.domain;

import org.cccs.parrot.Description;
import org.codehaus.jackson.annotate.JsonIgnore;
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
@javax.persistence.Entity
@Table
public class Cat {

    private Long id;
    private String name;
    private Person owner;
    private Set<Country> countries;

    public Cat() {
        this(null, null);
    }

    public Cat(String name) {
        this(name, null);
    }

    public Cat(final String name, final Person owner) {
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
    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @NotNull(message = "Owner must be specified")
    @ForeignKey(name = "FK_CAT_OWNER")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Person getOwner() {
        return owner;
    }

    @Description("Countries")
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
                '}';
    }
}

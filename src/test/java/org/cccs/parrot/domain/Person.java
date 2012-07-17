package org.cccs.parrot.domain;

import org.cccs.parrot.Description;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 17:08
 */
@javax.persistence.Entity
@Table(name = "Person",
    uniqueConstraints = {
        @UniqueConstraint(name = "UC_PERSON_NAME", columnNames = {"name", "email", "phone"})
    }
)
public class Person {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Set<Dog> dogs;
    private Set<Cat> cats;

    public Person() {
        this(null, null, null);
    }

    public Person(String name) {
        this(name, null, null);
    }

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dogs = new HashSet<Dog>();
        this.cats = new HashSet<Cat>();
    }

    @Description("ID")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Description("Name")
    @NotEmpty(message = "Name must be specified")
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Description("Email")
    @NotEmpty(message = "Email must be specified")
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Description("Phone")
    @NotEmpty(message = "Phone must be specified")
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Set<Dog> getDogs() {
        return dogs;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Set<Cat> getCats() {
        return cats;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

package org.cccs.parrot.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 17:08
 */
@Entity
@Table(name = "Person")
public class Person {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Set<Dog> dogs;
    private Set<Cat> cats;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(unique = true, name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "person_id")
    public Set<Dog> getDogs() {
        return dogs;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "person_id")
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

        if (!id.equals(person.id)) return false;
        if (!name.equals(person.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

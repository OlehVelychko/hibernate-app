package ua.com.alicecompany.model;

import jakarta.persistence.*;

@Entity
@Table(name = "principal")
public class Principal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(mappedBy = "principal", cascade = CascadeType.ALL, orphanRemoval = true)
    private School school;

    public Principal() {
    }

    public Principal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                // Avoid recursive call to School's toString()
                ", school_id=" + (school != null ? school.getId() : "null") +
                '}';
    }
}

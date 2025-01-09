package ua.com.alicecompany.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "animal_habitats",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "habitat_id")
    )
    private Set<Habitat> habitats = new HashSet<>();

    public Animal() {}

    public Animal(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Habitat> getHabitats() {
        return habitats;
    }

    public void setHabitats(Set<Habitat> habitats) {
        this.habitats = habitats;
    }

    public void addHabitat(Habitat habitat) {
        habitats.add(habitat);
        habitat.getAnimals().add(this);
    }

    public void removeHabitat(Habitat habitat) {
        habitats.remove(habitat);
        habitat.getAnimals().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
package ua.com.alicecompany.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "habitats")
public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "habitats")
    private Set<Animal> animals = new HashSet<>();

    public Habitat() {}

    public Habitat(String name) {
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

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.getHabitats().add(this);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        animal.getHabitats().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitat)) return false;
        Habitat habitat = (Habitat) o;
        return Objects.equals(name, habitat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
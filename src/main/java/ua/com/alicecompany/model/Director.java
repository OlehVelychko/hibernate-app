package ua.com.alicecompany.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "director")
public class Director {

    @Id
    @Column(name = "director_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int director_id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;

    public Director() {
    }

    public Director(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Director{" +
                "director_id=" + director_id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
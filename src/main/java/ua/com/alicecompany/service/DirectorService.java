package ua.com.alicecompany.service;

import org.hibernate.Session;
import ua.com.alicecompany.model.Director;
import ua.com.alicecompany.model.Movie;

import java.util.ArrayList;
import java.util.Collections;

public class DirectorService {

    public void listDirectorMovies(Session session, int directorId) {
        Director director = session.get(Director.class, directorId);
        if (director != null) {
            System.out.printf("%s's movies:\n", director.getName());
            director.getMovies().forEach(System.out::println);
        } else {
            System.out.println("Director not found.");
        }
    }

    public void getMovieDirector(Session session, int movieId) {
        Movie movie = session.get(Movie.class, movieId);
        if (movie != null) {
            System.out.printf("Director of '%s' is %s\n", movie.getName(), movie.getDirector().getName());
        } else {
            System.out.println("Movie not found.");
        }
    }

    public void addMovieToDirector(Session session, int directorId, String movieName, int year) {
        Director director = session.get(Director.class, directorId);
        if (director != null) {
            Movie newMovie = new Movie(movieName, year, director);
            director.getMovies().add(newMovie);
            session.persist(newMovie);
            System.out.printf("Added movie '%s' to %s.\n", movieName, director.getName());
        } else {
            System.out.println("Director not found.");
        }
    }

    public void createDirectorWithMovie(Session session, String directorName, int age, String movieName, int year) {
        Director director = new Director(directorName, age);
        Movie movie = new Movie(movieName, year, director);
        director.setMovies(new ArrayList<>(Collections.singletonList(movie)));
        session.persist(director);
        System.out.printf("Created director '%s' with movie '%s'.\n", directorName, movieName);
    }

    public void changeMovieDirector(Session session, int movieId, int newDirectorId) {
        Movie movie = session.get(Movie.class, movieId);
        Director newDirector = session.get(Director.class, newDirectorId);

        if (movie != null && newDirector != null) {
            movie.getDirector().getMovies().remove(movie);
            movie.setDirector(newDirector);
            newDirector.getMovies().add(movie);
            System.out.printf("Changed director of '%s' to %s.\n", movie.getName(), newDirector.getName());
        } else {
            System.out.println("Movie or Director not found.");
        }
    }

    public void deleteMovie(Session session, int directorId, int movieId) {
        Director director = session.get(Director.class, directorId);
        Movie movie = session.get(Movie.class, movieId);

        if (director != null && movie != null) {
            director.getMovies().remove(movie);
            session.remove(movie);
            System.out.printf("Deleted movie '%s' from %s's list.\n", movie.getName(), director.getName());
        } else {
            System.out.println("Director or Movie not found.");
        }
    }
}
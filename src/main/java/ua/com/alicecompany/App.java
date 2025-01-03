package ua.com.alicecompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alicecompany.model.Director;
import ua.com.alicecompany.model.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
            // Load configuration from hibernate.yaml
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            Map<String, Object> yamlConfig = yamlMapper.readValue(new File("src/main/resources/hibernate.yaml"), Map.class);
            Map<String, String> flatConfig = ConfigFlattener.flattenConfig((Map<String, Object>) yamlConfig.get("hibernate"), "hibernate");

            // Configure Hibernate with flat settings
            Configuration configuration = new Configuration();
            flatConfig.forEach(configuration::setProperty);

            configuration.addAnnotatedClass(Director.class);
            configuration.addAnnotatedClass(Movie.class);

            // Initialize session factory
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // Retrieve a director and list their movies
                Director director = session.get(Director.class, 2);
                System.out.printf("%s's movies:\n", director.getName());
                director.getMovies().forEach(System.out::println);

                // Retrieve a movie and its director
                Movie movie = session.get(Movie.class, 11);
                System.out.printf("Director of '%s' is %s\n", movie.getName(), movie.getDirector().getName());

                // Add a new movie to an existing director
                Director directorCN = session.get(Director.class, 6);
                Movie newMovieByCN = new Movie("Interstellar", 2014, directorCN);
                directorCN.getMovies().add(newMovieByCN);
                session.persist(newMovieByCN); // Persist the new movie
                System.out.printf("%s's movies after addition:\n", directorCN.getName());
                directorCN.getMovies().forEach(System.out::println);

                // Create a new director and their movie
                Director directorPJ = new Director("Peter Jackson", 63);
                Movie movieByPJ = new Movie("The Lord of the Rings trilogy", 2001, directorPJ);
                directorPJ.setMovies(new ArrayList<>(Collections.singletonList(movieByPJ)));
                session.persist(directorPJ); // Persist the director (cascades movie)
                System.out.printf("%s's movies:\n", directorPJ.getName());
                directorPJ.getMovies().forEach(System.out::println);

                // Change the director of an existing movie
                Movie movieToChangeDirector = session.get(Movie.class, 3);
                Director directorDL = session.get(Director.class, 5);
                movieToChangeDirector.getDirector().getMovies().remove(movieToChangeDirector); // Remove from current director
                movieToChangeDirector.setDirector(directorDL); // Set new director
                directorDL.getMovies().add(movieToChangeDirector); // Add to new director's movies
                System.out.printf("%s's movies after change:\n", directorDL.getName());
                directorDL.getMovies().forEach(System.out::println);

                // Delete a movie from a director
                Director directorGR = session.get(Director.class, 3);
                Movie movieGR = session.get(Movie.class, 9);
                directorGR.getMovies().remove(movieGR); // Remove the movie
                session.remove(movieGR); // Delete the movie entity
                System.out.printf("%s's movies after deletion:\n", directorGR.getName());
                directorGR.getMovies().forEach(System.out::println);

                session.getTransaction().commit();
            } finally {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
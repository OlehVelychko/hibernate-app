package ua.com.alicecompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alicecompany.model.*;
import ua.com.alicecompany.service.AnimalService;

import java.io.File;
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
            configuration.addAnnotatedClass(Principal.class);
            configuration.addAnnotatedClass(School.class);
            configuration.addAnnotatedClass(Animal.class);
            configuration.addAnnotatedClass(Habitat.class);

            // Initialize session factory
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            // Service for Principal and School operations
            AnimalService animalService = new AnimalService();

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // Create animals with habitats
                animalService.createAnimalWithHabitats(session, "Tiger", "Forest", "River");
                animalService.createAnimalWithHabitats(session, "Elephant", "Forest", "Savannah");

                // Add a habitat to an existing animal
                animalService.addHabitatToAnimal(session, 1, "Mountain");

                // List habitats of an animal
                animalService.listAnimalHabitats(session, 1);

                // Remove a habitat from an animal
                animalService.removeHabitatFromAnimal(session, 1, 3);

                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
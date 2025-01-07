package ua.com.alicecompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alicecompany.model.Director;
import ua.com.alicecompany.model.Movie;
import ua.com.alicecompany.model.Principal;
import ua.com.alicecompany.model.School;
import ua.com.alicecompany.service.DirectorService;
import ua.com.alicecompany.service.PrincipalService;

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

            // Initialize session factory
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            // Service for Principal and School operations
            PrincipalService principalService = new PrincipalService();

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // Test different operations
                principalService.getPrincipalWithSchool(session, 3);
                principalService.getSchoolWithPrincipal(session, 2);
                principalService.createPrincipalWithSchool(session, "Eve", 50, 77);

                // Method occurs an error
                principalService.changePrincipalOfSchool(session, 3, 1);

                principalService.addSchoolForPrincipal(session, 1, 99);

                session.getTransaction().commit();
            } finally {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
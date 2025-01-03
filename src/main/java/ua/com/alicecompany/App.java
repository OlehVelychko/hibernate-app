package AliceCompany;

import AliceCompany.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.beginTransaction();

            currentSession.createQuery("UPDATE Person SET name = 'Test' WHERE age < 2", Person.class).executeUpdate();

            currentSession.getTransaction().commit();
        } finally {
            sessionFactory.close();

        }
    }
}

package ua.com.alicecompany.service;

import org.hibernate.Session;
import ua.com.alicecompany.model.Animal;
import ua.com.alicecompany.model.Habitat;

public class AnimalService {

    public void createAnimalWithHabitats(Session session, String animalName, String... habitatNames) {
        Animal animal = new Animal(animalName);
        for (String habitatName : habitatNames) {
            Habitat habitat = new Habitat(habitatName);
            animal.addHabitat(habitat);
        }
        session.persist(animal);
        System.out.printf("Created animal '%s' with habitats %s.\n", animalName, animal.getHabitats());
    }

    public void addHabitatToAnimal(Session session, int animalId, String habitatName) {
        Animal animal = session.get(Animal.class, animalId);
        Habitat habitat = new Habitat(habitatName);
        if (animal != null) {
            animal.addHabitat(habitat);
            session.persist(habitat);
            System.out.printf("Added habitat '%s' to animal '%s'.\n", habitatName, animal.getName());
        } else {
            System.out.println("Animal not found.");
        }
    }

    public void removeHabitatFromAnimal(Session session, int animalId, int habitatId) {
        Animal animal = session.get(Animal.class, animalId);
        Habitat habitat = session.get(Habitat.class, habitatId);
        if (animal != null && habitat != null) {
            animal.removeHabitat(habitat);
            System.out.printf("Removed habitat '%s' from animal '%s'.\n", habitat.getName(), animal.getName());
        } else {
            System.out.println("Animal or Habitat not found.");
        }
    }

    public void listAnimalHabitats(Session session, int animalId) {
        Animal animal = session.get(Animal.class, animalId);
        if (animal != null) {
            System.out.printf("Habitats of '%s': %s\n", animal.getName(), animal.getHabitats());
        } else {
            System.out.println("Animal not found.");
        }
    }
}
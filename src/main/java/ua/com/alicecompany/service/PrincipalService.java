package ua.com.alicecompany.service;

import org.hibernate.Session;
import ua.com.alicecompany.model.Principal;
import ua.com.alicecompany.model.School;

public class PrincipalService {

    // Retrieve a principal and their school
    public void getPrincipalWithSchool(Session session, int principalId) {
        Principal principal = session.get(Principal.class, principalId);
        if (principal != null) {
            System.out.println("Principal: " + principal);
            System.out.println("School: " + principal.getSchool());
        } else {
            System.out.println("Principal not found.");
        }
    }

    // Retrieve a school and its principal
    public void getSchoolWithPrincipal(Session session, int schoolId) {
        School school = session.get(School.class, schoolId);
        if (school != null) {
            System.out.println("School: " + school);
            System.out.println("Principal: " + school.getPrincipal());
        } else {
            System.out.println("School not found.");
        }
    }

    // Create a new principal and a new school
    public void createPrincipalWithSchool(Session session, String principalName, int age, int schoolNumber) {
        Principal principal = new Principal(principalName, age);
        School school = new School(schoolNumber, principal);
        principal.setSchool(school);
        session.persist(principal); // Persist principal (cascade will handle the school)
        System.out.printf("Created Principal '%s' with School number %d.\n", principalName, schoolNumber);
    }

    // Change the principal of an existing school
    public void changePrincipalOfSchool(Session session, int schoolId, int newPrincipalId) {
        School school = session.get(School.class, schoolId);
        Principal newPrincipal = session.get(Principal.class, newPrincipalId);
        if (school != null && newPrincipal != null) {
            school.setPrincipal(newPrincipal);
            System.out.printf("Changed Principal of School %d to '%s'.\n", school.getSchool_number(), newPrincipal.getName());
        } else {
            System.out.println("School or Principal not found.");
        }
    }

    // Attempt to add another school for the same principal (violates constraint)
    public void addSchoolForPrincipal(Session session, int principalId, int schoolNumber) {
        Principal principal = session.get(Principal.class, principalId);
        if (principal != null) {
            School newSchool = new School(schoolNumber, principal);
            session.persist(newSchool);
            System.out.printf("Added another school (%d) for Principal '%s'.\n", schoolNumber, principal.getName());
        } else {
            System.out.println("Principal not found.");
        }
    }
}
package ua.com.alicecompany.model;

import jakarta.persistence.*;

@Entity
@Table(name = "school")
public class School {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "school_number", nullable = false)
    private int school_number;

    @OneToOne
    @JoinColumn(name = "principal_id", referencedColumnName = "id", unique = true)
    private Principal principal;

    public School() {
    }

    public School(int school_number, Principal principal) {
        this.school_number = school_number;
        this.principal = principal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public int getSchool_number() {
        return school_number;
    }

    public void setSchool_number(int school_number) {
        this.school_number = school_number;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", school_number=" + school_number +
                // Avoid recursive call to Principal's toString()
                ", principal_id=" + (principal != null ? principal.getId() : "null") +
                '}';
    }
}

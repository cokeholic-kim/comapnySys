package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Position position;
    @ManyToOne
    private Team team;

    public Employee() {}

    public Employee(Long id, String name, Position position, Team team) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.team = team;
    }
}

package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private String teamLeaderId;

    public Team() {
    }

    public Team(String positionName) {
        this.teamName = positionName;
    }
}

package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    @OneToOne
    @JoinColumn(name = "team_leader_id")
    private Employee teamLeader;
    private Integer annualLeaveBefore;

    @OneToMany(mappedBy = "team")
    private List<Employee> employeeList;

    public Team(String positionName) {
        this.teamName = positionName;
    }

    public void setTeamLeader(Employee leader) {
        this.teamLeader = leader;
    }
}

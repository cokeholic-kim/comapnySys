package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private Integer teamLeaderId;

    @OneToMany(mappedBy = "team")
    private List<Employee> employeeList;

    public Team() {
    }

    public Team(String positionName) {
        this.teamName = positionName;
    }

    public void setTeamLeader(Integer leaderCode) {
        this.teamLeaderId = leaderCode;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getTeamLeaderId() {
        return teamLeaderId;
    }

    public Employee findEmployee(String userName) {
        return employeeList.stream().filter(employee -> employee.getName().equals(userName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

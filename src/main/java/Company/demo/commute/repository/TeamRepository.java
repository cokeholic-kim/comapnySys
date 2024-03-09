package Company.demo.commute.repository;

import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.response.TeamResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByTeamName(String teamName);

    Boolean existsByTeamName(String teamName);

    @Query("SELECT new Company.demo.commute.dto.response.TeamResponse(a.teamName, b.name, COUNT(c.id)) " +
            "FROM Team a " +
            "LEFT JOIN Employee b ON a.teamLeader = b " +
            "LEFT JOIN Employee c ON a.teamId = c.team.teamId " +
            "GROUP BY a.teamName, b.name")
    List<TeamResponse> getAllTeam();
}

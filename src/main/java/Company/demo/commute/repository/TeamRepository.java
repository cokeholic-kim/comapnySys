package Company.demo.commute.repository;

import Company.demo.commute.dao.Team;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByTeamName(String teamName);

    @Query(value = "SELECT A.team_name AS name, B.name AS manager, COUNT(C.id) AS count " +
            "FROM team AS A " +
            "LEFT JOIN employee AS B ON A.team_leader_id = B.id " +
            "LEFT JOIN employee AS C ON A.team_id = C.team_id " +
            "GROUP BY A.team_name, B.name", nativeQuery = true)
    List<Map<String, Object>> findTeamDetailsWithManagerAndCount();
}

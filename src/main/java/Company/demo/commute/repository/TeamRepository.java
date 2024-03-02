package Company.demo.commute.repository;

import Company.demo.commute.dao.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}

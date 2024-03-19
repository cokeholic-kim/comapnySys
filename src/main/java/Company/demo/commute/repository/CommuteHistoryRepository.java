package Company.demo.commute.repository;

import Company.demo.commute.dao.CommuteHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteHistoryRepository extends JpaRepository<CommuteHistory, Long> {

}

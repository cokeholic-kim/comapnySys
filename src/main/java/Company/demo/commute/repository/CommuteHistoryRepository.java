package Company.demo.commute.repository;

import Company.demo.commute.dao.CommuteHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommuteHistoryRepository extends JpaRepository<CommuteHistory, Long> {
    @Query(value = "SELECT DATE(start_time) as start_date, SUM(TIMESTAMPDIFF(MINUTE, start_time, end_time)) AS total_time_in_minutes FROM commute_history WHERE employee_id = :employeeId AND YEAR(start_time) = :year AND MONTH(start_time) = :month GROUP BY start_date", nativeQuery = true)
    List<Object[]> findworkTime(@Param("employeeId") Long employeeId, @Param("year") int year,
                                @Param("month") int month);
}

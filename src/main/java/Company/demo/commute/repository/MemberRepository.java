package Company.demo.commute.repository;

import Company.demo.commute.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Employee, Long> {
    Employee findByName(String name);

//    @Query("SELECT new Company.demo.commute.dto.response.OverTimeResponse(a.id, a.name, SUM(function('TIMESTAMPDIFF', b.startTime, b.endTime) )) " +
//            "FROM Employee a " +
//            "LEFT outer JOIN CommuteHistory b ON a = b.employee " +
//            "WHERE b.startTime >= :startDate AND b.startTime <= :endDate " +
//            "GROUP BY a")
//    List<OverTimeResponse> getAllByYearMonthOverTimeResponse(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}

package Company.demo.commute.repository;

import Company.demo.commute.dao.AnnualLeaveRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveRegister,Long> {
}

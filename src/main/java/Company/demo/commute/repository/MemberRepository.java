package Company.demo.commute.repository;

import Company.demo.commute.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Employee,Long> {
    Employee findByName(String name);
}

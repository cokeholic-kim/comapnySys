package Company.demo.commute.repository;

import Company.demo.commute.dao.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {
    Position findByPositionName(String positionName);
}

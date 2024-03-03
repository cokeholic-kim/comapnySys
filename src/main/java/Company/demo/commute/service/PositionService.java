package Company.demo.commute.service;

import Company.demo.commute.dao.Position;
import Company.demo.commute.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository repository;

    public void savePosition(String positionName){
        System.out.println("positionName = " + positionName);
        repository.save(new Position(positionName));
    }

}

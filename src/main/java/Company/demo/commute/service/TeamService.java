package Company.demo.commute.service;

import Company.demo.commute.dao.Team;
import Company.demo.commute.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public void registerTeam(String teamName){
        repository.save(new Team(teamName));
    }
}

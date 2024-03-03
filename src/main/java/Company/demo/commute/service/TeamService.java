package Company.demo.commute.service;

import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.request.TeamSetDto;
import Company.demo.commute.dto.response.TeamResponse;
import Company.demo.commute.repository.TeamRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public void registerTeam(String teamName) {
        repository.save(new Team(teamName));
    }

    @Transactional
    public void registerTeamLeader(TeamSetDto request) {
        Team team = repository.findByTeamName(request.getTeamName());
        team.setTeamLeader(request.getLeader_code());
    }

    public List<TeamResponse> getTeam() {
        return repository.findTeamDetailsWithManagerAndCount().stream()
                .map(result ->{
                    TeamResponse response = new TeamResponse();
                    response.setName((String) result.get("name"));
                    response.setManager((String) result.get("manager"));
                    response.setCount((long)result.get("count"));
                    return response;
                })
                .collect(Collectors.toList());
    }
}

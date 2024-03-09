package Company.demo.commute.service;

import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.request.TeamSetDto;
import Company.demo.commute.dto.response.TeamResponse;
import Company.demo.commute.repository.MemberRepository;
import Company.demo.commute.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository repository;
    private final MemberRepository memberRepository;

    @Transactional
    public void registerTeam(String teamName) {
        if (isExistTeam(teamName)) {
            throw new IllegalArgumentException("이미 존재하는 팀 이름입니다: " + teamName);
        }
        repository.save(new Team(teamName));
    }

    @Transactional
    public void registerTeamLeader(TeamSetDto request) {
        Team team = repository.findByTeamName(request.getTeamName());
        team.setTeamLeader(memberRepository.findById(Long.valueOf(request.getLeaderCode()))
                .orElseThrow(IllegalArgumentException::new));
    }

    @Transactional
    public List<TeamResponse> getTeam() {
        return repository.getAllTeam();
    }

    private boolean isExistTeam(String name) {
        return repository.existsByTeamName(name);
    }
}

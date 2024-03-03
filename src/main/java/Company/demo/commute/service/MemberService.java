package Company.demo.commute.service;

import Company.demo.commute.dao.Employee;
import Company.demo.commute.dao.Position;
import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.repository.MemberRepository;
import Company.demo.commute.repository.PositionRepository;
import Company.demo.commute.repository.TeamRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PositionRepository positionRepository;
    private final TeamRepository teamRepository;
    @Transactional
    public void saveMember(MemberSaveRequest request) {

        Position position = positionRepository.findByPositionName(request.getPositionName());

        repository.save(new Employee(request, position));
    }
    @Transactional
    public void setTeam(MemberTeamSetDto request) {
        Employee target = repository.findByName(request.getEmployeeName());
        Team team = teamRepository.findById(Long.valueOf(request.getTeamId()))
                .orElseThrow(IllegalArgumentException::new);
        target.setTeam(team);
    }

    public List<EmployeeResponse> getEmployee() {
        return repository.findAll().stream()
                .map(result->{
                    EmployeeResponse response = new EmployeeResponse();
                    response.setName(result.getName());
                    response.setBirthDay(result.getBirthDay());
                    response.setRole(Optional.ofNullable(result.getPosition()).map(Position::getPositionName).orElse(""));
                    response.setTeamName(Optional.ofNullable(result.getTeam()).map(Team::getTeamName).orElse(""));
                    response.setWorkStartDate(result.getStartDate());
                    return response;
                }).collect(Collectors.toList());
    }
}

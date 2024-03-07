package Company.demo.commute.service;

import Company.demo.commute.dao.AnnualLeaveRegister;
import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.CompanyPolicy;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dao.Position;
import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.request.ALRequest;
import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.dto.response.OverTimeResponse;
import Company.demo.commute.repository.AnnualLeaveRepository;
import Company.demo.commute.repository.CommuteHistoryRepository;
import Company.demo.commute.repository.CompanyPolicyRepository;
import Company.demo.commute.repository.MemberRepository;
import Company.demo.commute.repository.PositionRepository;
import Company.demo.commute.repository.TeamRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private final AnnualLeaveRepository AlRepository;
    private final CommuteHistoryRepository commuteRepository;
    private final CompanyPolicyRepository policyRepository;

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
                .map(result -> {
                    EmployeeResponse response = new EmployeeResponse();
                    response.setName(result.getName());
                    response.setBirthDay(result.getBirthDay());
                    response.setRole(
                            Optional.ofNullable(result.getPosition()).map(Position::getPositionName).orElse(""));
                    response.setTeamName(Optional.ofNullable(result.getTeam()).map(Team::getTeamName).orElse(""));
                    response.setWorkStartDate(result.getStartDate());
                    return response;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void registerAl(ALRequest request) {
        Employee employee = repository.findById(request.getEmployeeId()).orElseThrow(IllegalArgumentException::new);
        Team team = teamRepository.findById(employee.getTeam().getTeamId()).orElseThrow(IllegalArgumentException::new);
        Integer annualLeaveBefore = team.getAnnualLeaveBefore();
        long between = ChronoUnit.DAYS.between(LocalDate.now(), request.getStartDate());
        if (between < 0 || between < annualLeaveBefore) {
            System.out.print("휴가 신청이 불가능합니다.");
        } else {
            AlRepository.save(new AnnualLeaveRegister(employee, request.getStartDate(), request.getEndDate()));
            employee.useAL((int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1);
        }
    }

    public Integer getAnnualLeave(Integer employeeId) {
        Employee employee = repository.findById(Long.valueOf(employeeId)).orElseThrow(IllegalArgumentException::new);
        return employee.getAnnualLeave();
    }

    @Transactional
    public List<OverTimeResponse> getOverTime(String yearMonth, Integer holidays) {
        CompanyPolicy policy = policyRepository.findByPolicyGubnAndPolicyName("workTime", "dailyWorkLimit");
        int standardWorkHour = calcStandardWorkDays(yearMonth, holidays) * Integer.parseInt(policy.getPolicyContent());
        List<Employee> employeeList = repository.findAll();
        List<OverTimeResponse> collect = employeeList.stream().map(result -> {
            OverTimeResponse response = new OverTimeResponse();
            response.setId(Math.toIntExact(result.getId()));
            response.setName(result.getName());
            System.out.println("result.getName() = " + result.getName());
            response.setOvertimeMinutes(
                    getOverTime(calcEmployeeWorkHour(result.getCommuteHistories(), yearMonth), standardWorkHour));
            return response;
        }).collect(Collectors.toList());
        return collect;
    }

    private Integer getOverTime(Integer workTime, Integer standardWorkHour) {
        System.out.println("workTime = " + workTime);
        System.out.println("standardWorkHour = " + standardWorkHour);
        Integer standardWorkMinute = standardWorkHour * 60;
        if (workTime > standardWorkMinute) {
            return workTime - standardWorkMinute;
        } else {
            return 0;
        }
    }

    private Integer calcEmployeeWorkHour(List<CommuteHistory> commuteHistories, String yearMonth) {
        return commuteHistories.stream().filter(result -> isSameYearMonth(yearMonth, result))
                .mapToInt(result -> diffMinute(result.getStartTime(), result.getEndTime()))
                .sum();
    }

    private Integer diffMinute(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return (int) duration.toMinutes();
    }

    private static boolean isSameYearMonth(String yearMonth, CommuteHistory queryResult) {
        YearMonth yearMonthFromQuery = YearMonth.from(queryResult.getStartTime().toLocalDate());
        YearMonth yearMonthFromRequest = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        return yearMonthFromQuery.equals(yearMonthFromRequest);
    }

    private Integer calcStandardWorkDays(String yearMonth, Integer holidays) {
        int daysInMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM")).lengthOfMonth();
        return daysInMonth - holidays;
    }
}

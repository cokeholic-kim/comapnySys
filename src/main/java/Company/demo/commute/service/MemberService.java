package Company.demo.commute.service;

import Company.demo.commute.dao.CompanyPolicy;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.firstCollection.ListEmployeeResponse;
import Company.demo.commute.dto.firstCollection.OverTimeResponseList;
import Company.demo.commute.dto.request.MemberPositionSetDto;
import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.request.OverTimeRequest;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.dto.response.OverTimeResponse;
import Company.demo.commute.repository.CompanyPolicyRepository;
import Company.demo.commute.repository.MemberRepository;
import Company.demo.commute.repository.TeamRepository;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final TeamRepository teamRepository;
    private final CompanyPolicyRepository policyRepository;

    @Transactional
    public void saveMember(MemberSaveRequest request) {
        repository.save(new Employee(request));
    }

    @Transactional
    public void setTeam(MemberTeamSetDto request) {
        Employee target = repository.findByName(request.getEmployeeName());
        Team team = teamRepository.findById(Long.valueOf(request.getTeamId()))
                .orElseThrow(IllegalArgumentException::new);
        target.setTeam(team);
    }

    @Transactional
    public void setPosition(MemberPositionSetDto request) {
        Employee employee = repository.findByName(request.getEmployeeName());
        Team team = teamRepository.findById(request.getPositionId())
                .orElseThrow(IllegalArgumentException::new);
        employee.setTeam(team);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployee() {
        return new ListEmployeeResponse(repository.findAll()).getEmployeeResponseList();
    }

    @Transactional
    public List<OverTimeResponse> getOverTime(OverTimeRequest request) {
        YearMonth yearMonth = request.getYearMonth();
        Integer holidays = request.getHolidays();

        //근무규정의 일 근무시간을 조회
        CompanyPolicy policy = policyRepository.findByPolicyGubnAndPolicyName("workTime", "dailyWorkLimit")
                .orElseThrow(() -> new IllegalArgumentException("없는 정책입니다."));
        //해당하는 달의 총 평균근무시간을 계산 근무일 * 근무시간
        int standardWorkHour = calcStandardWorkDays(yearMonth, holidays) * Integer.parseInt(policy.getPolicyContent());
        //TODO 날짜정보로 직원정보를 조회해와서 사용할수있도록 수정.
        List<Employee> employeeList = repository.findAll();
        OverTimeResponseList overTimeResponseList = new OverTimeResponseList(employeeList, yearMonth, standardWorkHour);
        return overTimeResponseList.getOverTimeResponseList();
    }


    private Integer calcStandardWorkDays(YearMonth yearMonth, Integer holidays) {
        int daysInMonth = yearMonth.lengthOfMonth();
        return daysInMonth - holidays;
    }

}

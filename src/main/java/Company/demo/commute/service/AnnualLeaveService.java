package Company.demo.commute.service;

import Company.demo.commute.dao.AnnualLeaveRegister;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.request.ALRequest;
import Company.demo.commute.dto.response.ResponseAnnualLeave;
import Company.demo.commute.repository.AnnualLeaveRepository;
import Company.demo.commute.repository.CompanyPolicyRepository;
import Company.demo.commute.repository.MemberRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnualLeaveService {
    private final MemberRepository memberRepository;
    private final AnnualLeaveRepository annualLeaveRepository;
    private final CompanyPolicyRepository policyRepository;

    @Transactional
    public void registerAl(ALRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Employee employee = memberRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("직원 정보가없습니다. "));
        //TODO 예외처리부분 분리 필요
        //팀 휴가 사전보고일을 조회해서 사전보고일보다 늦게 보고시 에러처리
        validateExceedAdvancedReportDate(employee, startDate);
        //연차를 신청한기간에 이미 연차등록이있으면 예외처리
        validateALRedundant(employee, endDate, startDate);
        //연차 등록정보를 등록
        annualLeaveRepository.save(new AnnualLeaveRegister(employee, startDate, endDate));
        int annualLeaveDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //연차 신청기간이 직원의 잔여 연차보다 크면 예외처리
        validateEmployeeRemainAL(annualLeaveDays, employee);
        //연차를사용한 직원의 연차를 차감.
        employee.useAL(annualLeaveDays);
    }

    private static void validateEmployeeRemainAL(int annualLeaveDays, Employee employee) {
        if (annualLeaveDays > employee.getAnnualLeave()) {
            throw new IllegalArgumentException(
                    String.format("휴가 신청이 불가능합니다. 남은 연차 (%d) 신청한연차기간 (%d)", employee.getAnnualLeave(),
                            annualLeaveDays));
        }
    }

    private static void validateExceedAdvancedReportDate(Employee employee, LocalDate startDate) {
        Integer annualLeaveBefore = employee.getTeam().getAnnualLeaveBefore();
        long between = ChronoUnit.DAYS.between(LocalDate.now(), startDate);
        if (between < 0 || between < annualLeaveBefore) {
            throw new IllegalArgumentException(String.format("휴가 신청이 불가능합니다. %d 일 전에 팀에 보고해야 합니다.", annualLeaveBefore));
        }
    }

    private static void validateALRedundant(Employee employee, LocalDate endDate, LocalDate startDate) {
        List<AnnualLeaveRegister> annualLeaveRegisters = employee.getAnnualLeaveRegisters();
        annualLeaveRegisters.stream().forEach(it -> {
            LocalDate empStartDate = it.getStartDate();
            LocalDate empEndDate = it.getEndDate();
            if (!(endDate.isBefore(empStartDate) || startDate.isAfter(empEndDate))) {
                throw new IllegalArgumentException("신청한 휴가 기간이 이미 신청된 휴가 기간과 겹칩니다.");
            }
        });
    }

    @Transactional(readOnly = true)
    public ResponseAnnualLeave getAnnualLeave(Integer employeeId) {
        Employee employee = memberRepository.findById(Long.valueOf(employeeId))
                .orElseThrow(() -> new IllegalArgumentException("직원 정보가없습니다. "));
        return new ResponseAnnualLeave(employee.getAnnualLeave());
    }

    @Transactional
    public void setAnnualLeave() {
        int newEmployee = getAnnualLeavePolicy("newEmployee");
        int experienced = getAnnualLeavePolicy("experienced");
        memberRepository.findAll().stream().forEach(employee -> {
            //올해
            int thisYear = LocalDate.now().getYear();
            //직원의 입사연도정보
            int employeeStartYear = employee.getStartDate().getYear();
            if (employeeStartYear == thisYear) {
                employee.setAnnualLeave(newEmployee);
            } else {
                employee.setAnnualLeave(experienced);
            }
        });
    }

    private int getAnnualLeavePolicy(String employeeStatus) {
        String policyGubn = "annualLeave";
        return Integer.parseInt(policyRepository.findByPolicyGubnAndPolicyName(policyGubn, employeeStatus)
                .orElseThrow(() -> new IllegalArgumentException("없는 정책입니다."))
                .getPolicyContent());
    }
}

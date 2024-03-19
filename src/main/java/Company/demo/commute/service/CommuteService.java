package Company.demo.commute.service;

import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.firstCollection.EmployeeTodayCommuteList;
import Company.demo.commute.dto.firstCollection.commutetimeresponse.CommuteTimeResponseList;
import Company.demo.commute.dto.request.CommuteGetDto;
import Company.demo.commute.dto.response.CommuteTotalResponse;
import Company.demo.commute.repository.CommuteHistoryRepository;
import Company.demo.commute.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommuteService {

    private final CommuteHistoryRepository repository;
    private final MemberRepository memberRepository;

    @Transactional
    public void checkWork(Long employeeId) {
        //해당 아이디의 직원을 조회
        Employee employee = memberRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원 정보가없습니다."));
        //직원의 금일 근무정보가 있는지 조회
        EmployeeTodayCommuteList employeeTodayCommuteList = new EmployeeTodayCommuteList(employee);
        checkStartOrEndWorking(employeeTodayCommuteList, employee);
    }

    private void checkStartOrEndWorking(EmployeeTodayCommuteList employeeTodayCommuteList, Employee employee) {
        // 금일근무정보가 없거나 ,근무기록은 있지만 근무중이 아닌경우(이미퇴근) ,새로 출근기록 생성
        if (employeeTodayCommuteList.isWorkEnd()) {
            CommuteHistory save = repository.save(new CommuteHistory(employee));
            save.startWorking();
        } else {
            //출근 기록이있는경우 퇴근으로
            employeeTodayCommuteList.getWorkingCommuteHistory().endWorking();
        }
    }

    @Transactional(readOnly = true)
    public CommuteTotalResponse checkTime(CommuteGetDto request) {
        Employee employee = memberRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("직원 정보가없습니다."));
        CommuteTimeResponseList commuteTimeResponseList = new CommuteTimeResponseList(request, employee);

        return new CommuteTotalResponse(commuteTimeResponseList.getCommuteTimeResponseList());
    }

}

package Company.demo.commute.service;

import Company.demo.commute.dao.AnnualLeaveRegister;
import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.request.CommuteGetDto;
import Company.demo.commute.dto.response.CommuteTimeResponse;
import Company.demo.commute.dto.response.CommuteTotalResponse;
import Company.demo.commute.repository.CommuteHistoryRepository;
import Company.demo.commute.repository.MemberRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public void startWork(Long employeeId) {
        Employee employee = memberRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        CommuteHistory result = isWorking(employee).orElse(null);

        if (result == null || Objects.nonNull(result.getEndTime())) {
            log.info("save new");
            //기록이없는 경우 출근 기록생성
            //퇴근 기록이있는경우 새로 출근 기록을 생성
            CommuteHistory commuteHistory = new CommuteHistory(employee);
            repository.save(commuteHistory);
            commuteHistory.startWorking();
        } else {
            log.info("update");
            //출근 기록이있는경우 퇴근으로
            result.endWorking();
        }
    }

    public CommuteTotalResponse checkTime(CommuteGetDto request) {
        String[] split = request.getSearchDate().split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        List<Object[]> objects = repository.findworkTime((long) request.getEmployeeId(), year, month);
        return new CommuteTotalResponse(objects.stream().map(result -> {
            CommuteTimeResponse response = new CommuteTimeResponse();
            response.setDate(result[0].toString());
            response.setWorkingMinutes(((Number) result[1]).intValue());
            return response;
        }).collect(Collectors.toList()));
    }

    public CommuteTotalResponse checkTime2(CommuteGetDto request) {
        Employee employee = memberRepository.findById(Long.valueOf(request.getEmployeeId()))
                .orElseThrow(IllegalArgumentException::new);
        List<CommuteTimeResponse> collect = getCommuteTimeResponses(request, employee.getCommuteHistories());
        List<AnnualLeaveRegister> annualLeaveRegisters = employee.getAnnualLeaveRegisters();
        annualLeaveRegisters.stream().forEach(leave -> {
            long daysBetween = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
            for (long i = 0; i <= daysBetween; i++) {
                LocalDate currentDate = leave.getStartDate().plusDays(i);
                collect.add(getCommuteTimeResponse(currentDate));
            }
        });
        List<CommuteTimeResponse> sortedList = collect.stream()
                .sorted(Comparator.comparing(response -> LocalDate.parse(response.getDate())))
                .toList();
        return new CommuteTotalResponse(sortedList);
    }

    private CommuteTimeResponse getCommuteTimeResponse(LocalDate currentDate) {
        CommuteTimeResponse commuteTimeResponse = new CommuteTimeResponse();
        commuteTimeResponse.setUsingDayOff(true);
        commuteTimeResponse.setWorkingMinutes(0);
        commuteTimeResponse.setDate(String.valueOf(currentDate));
        return commuteTimeResponse;
    }

    private List<CommuteTimeResponse> getCommuteTimeResponses(CommuteGetDto request,
                                                              List<CommuteHistory> commuteHistories) {
        List<CommuteTimeResponse> collect = commuteHistories.stream()
                .filter(queryResult -> isSameYearMonth(request, queryResult))
                .collect(Collectors.groupingBy(
                        result -> dateTimeToString(result.getStartTime()),
                        Collectors.summingInt(result -> diffMinute(result.getStartTime(), result.getEndTime()))
                ))
                .entrySet().stream()
                .map(result -> {
                    CommuteTimeResponse response = new CommuteTimeResponse();
                    response.setDate(result.getKey());
                    response.setWorkingMinutes(result.getValue());
                    return response;
                }).collect(Collectors.toList());
        return collect;
    }

    private static boolean isSameYearMonth(CommuteGetDto request, CommuteHistory queryResult) {
        YearMonth yearMonthFromQuery = YearMonth.from(queryResult.getStartTime().toLocalDate());
        YearMonth yearMonthFromRequest = YearMonth.parse(request.getSearchDate(),
                DateTimeFormatter.ofPattern("yyyy-MM"));
        return yearMonthFromQuery.equals(yearMonthFromRequest);
    }

    private String dateTimeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return time.format(formatter);
    }

    private Integer diffMinute(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return (int) duration.toMinutes();
    }

    private Optional<CommuteHistory> isWorking(Employee employee) {
        //현재시간과 유저정보로 출퇴근기록을 조회
        //기록이없는 경우 출근 기록생성
        //출근 기록이있는경우 퇴근으로
        //퇴근 기록이있는경우 새로 출근 기록을 생성
        Optional<CommuteHistory> commuteHistory = employee.findCommuteHistory();
        System.out.println("commuteHistory.orElse(null) = " + commuteHistory.orElse(null));
        return commuteHistory;
    }
}

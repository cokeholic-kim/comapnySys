package Company.demo.commute.dto.firstCollection.annualleavelist;

import Company.demo.commute.dao.AnnualLeaveRegister;
import Company.demo.commute.dto.response.CommuteTimeResponse;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AnnualLeaveToCommuteResponse {

    private List<AnnualLeaveRegister> annualLeaveRegisters;

    public AnnualLeaveToCommuteResponse(List<AnnualLeaveRegister> annualLeaveRegisters) {
        this.annualLeaveRegisters = annualLeaveRegisters;
    }

    public List<CommuteTimeResponse> leaveToResponse(YearMonth searchYearMonth){
        ArrayList<CommuteTimeResponse> collect = new ArrayList<>();
        annualLeaveRegisters.forEach(leave -> {
            long daysBetween = getSameMonthLeaveDaysBetween(searchYearMonth, leave);
            for (long i = 0; i <= daysBetween; i++) {
                LocalDate currentDate = leave.getStartDate().plusDays(i);
                collect.add(new CommuteTimeResponse(currentDate));
            }
        });
        return collect;
    }

    private static long getSameMonthLeaveDaysBetween(YearMonth yearMonth, AnnualLeaveRegister leave) {
        LocalDate startDate = leave.getStartDate();
        LocalDate endDate = leave.getEndDate();
        // 검색 시작일과 휴가 시작일 중 늦은 날짜를 선택
        LocalDate searchStart = yearMonth.atDay(1);
        LocalDate effectiveStartDate = startDate.isBefore(searchStart) ? searchStart : startDate;
        // 검색 종료일과 휴가 종료일 중 이른 날짜를 선택
        LocalDate searchEnd = yearMonth.atEndOfMonth();
        LocalDate effectiveEndDate = endDate.isAfter(searchEnd) ? searchEnd : endDate;

        return ChronoUnit.DAYS.between(effectiveStartDate, effectiveEndDate);
    }
}

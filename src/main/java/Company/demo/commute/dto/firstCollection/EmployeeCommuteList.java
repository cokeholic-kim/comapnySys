package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeCommuteList {
    private List<CommuteHistory> commuteHistoryList;

    public EmployeeCommuteList(Employee employee) {
        this.commuteHistoryList = employee.getCommuteHistories();
    }

    public EmployeeCommuteList filterByYearMonth(String yearMonth){
        this.commuteHistoryList = this.commuteHistoryList.stream().filter(
                result -> isSameYearMonth(yearMonth, result.getStartTime().toString())
        ).collect(Collectors.toList());
        return this;
    }


    public Integer calcEmployeeWorkHour() {
        return commuteHistoryList.stream()
                .mapToInt(result -> diffMinute(result.getStartTime(), result.getEndTime()))
                .sum();
    }

    private Integer diffMinute(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return (int) duration.toMinutes();
    }

    private static boolean isSameYearMonth(String yearMonth, String queryResult) {
        YearMonth yearMonthFromRequest = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        YearMonth yearMonthFromQuery = YearMonth.parse(queryResult, DateTimeFormatter.ofPattern("yyyy-MM"));
        return yearMonthFromQuery.equals(yearMonthFromRequest);
    }

}

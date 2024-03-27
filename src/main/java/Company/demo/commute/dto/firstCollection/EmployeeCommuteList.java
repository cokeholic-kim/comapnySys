package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeCommuteList {
    private List<CommuteHistory> commuteHistoryList;

    public EmployeeCommuteList(Employee employee) {
        this.commuteHistoryList = employee.getCommuteHistories();
    }

    public EmployeeCommuteList filterByYearMonth(YearMonth yearMonth) {
        this.commuteHistoryList = this.commuteHistoryList.stream().filter(
                result -> YearMonth.from(result.getStartTime()).equals(yearMonth)
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
}

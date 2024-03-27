package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.response.OverTimeResponse;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OverTimeResponseList {
    private List<OverTimeResponse> overTimeResponseList;

    public OverTimeResponseList(List<Employee> employeeList,YearMonth yearMonth,Integer standardWorkHour) {
        this.overTimeResponseList = employeeList.stream().map(result -> {
            Integer employeeWorkHour = new EmployeeCommuteList(result).filterByYearMonth(yearMonth).calcEmployeeWorkHour();
            return OverTimeResponse.builder()
                    .id(Math.toIntExact(result.getId()))
                    .name(result.getName())
                    .overtimeMinutes(getOverTime(employeeWorkHour, standardWorkHour))
                    .build();
        }).collect(Collectors.toList());
    }

    private Integer getOverTime(Integer workTime, Integer standardWorkHour) {
        Integer standardWorkMinute = standardWorkHour * 60;
        if (workTime > standardWorkMinute) {
            return workTime - standardWorkMinute;
        } else {
            return 0;
        }
    }
}

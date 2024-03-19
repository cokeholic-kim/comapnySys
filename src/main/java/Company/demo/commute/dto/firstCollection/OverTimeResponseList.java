package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.response.OverTimeResponse;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class OverTimeResponseList {
    private List<OverTimeResponse> overTimeResponseList;

    public OverTimeResponseList(List<Employee> employeeList,String yearMonth,Integer standardWorkHour) {
        this.overTimeResponseList = employeeList.stream().map(result -> {
            OverTimeResponse response = new OverTimeResponse();
            response.setId(Math.toIntExact(result.getId()));
            response.setName(result.getName());
            EmployeeCommuteList employeeCommuteList = new EmployeeCommuteList(result).filterByYearMonth(yearMonth);
            response.setOvertimeMinutes(getOverTime(employeeCommuteList.calcEmployeeWorkHour(), standardWorkHour));
            return response;
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

    private Integer calcStandardWorkDays(String yearMonth, Integer holidays) {
        int daysInMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM")).lengthOfMonth();
        return daysInMonth - holidays;
    }
}

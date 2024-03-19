package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.Employee;
import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.response.EmployeeResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ListEmployeeResponse {
    private final List<EmployeeResponse> employeeResponseList;

    public ListEmployeeResponse(List<Employee> employeeList) {
        this.employeeResponseList = employeeList.stream().map(result -> {
            EmployeeResponse response = new EmployeeResponse();
            response.setName(result.getName());
            response.setBirthDay(String.valueOf(result.getBirthDay()));
            response.setRole(result.getIsManager());
            response.setTeamName(Optional.ofNullable(result.getTeam()).map(Team::getTeamName).orElse(""));
            response.setWorkStartDate(String.valueOf(result.getStartDate()));
            return response;
        }).collect(Collectors.toList());
    }
}

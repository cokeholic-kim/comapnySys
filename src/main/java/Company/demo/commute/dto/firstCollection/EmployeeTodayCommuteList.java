package Company.demo.commute.dto.firstCollection;

import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.service.CommuteService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class EmployeeTodayCommuteList {
    private List<CommuteHistory> commuteHistoryList;

    public EmployeeTodayCommuteList(Employee employee) {
        this.commuteHistoryList = employee.getCommuteHistories().stream()
                .filter(target -> target.getStartTime().toLocalDate().equals(LocalDateTime.now().toLocalDate()))
                .collect(Collectors.toList());
    }

    public Boolean isWorkEnd(){
        return commuteHistoryList == null || commuteHistoryList.stream().noneMatch(CommuteHistory::getIsWorking);
    }

    public CommuteHistory getWorkingCommuteHistory(){
        return commuteHistoryList.stream().filter(CommuteHistory::getIsWorking)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

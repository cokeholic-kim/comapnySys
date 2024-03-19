package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.YearMonth;
import lombok.Getter;

@Entity
@Getter
public class CommuteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commuteId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CommuteHistory() {
    }

    public CommuteHistory(Employee employee) {
        this.employee = employee;
    }
    public Boolean getIsWorking(){
        return endTime == null;
    }


    public void startWorking(){
        this.startTime = LocalDateTime.now();
    }

    public void endWorking(){
        this.endTime = LocalDateTime.now();
    }

    public Boolean isSameYearMonth(YearMonth searchDate){
        YearMonth yearMonthFromQuery = YearMonth.from(startTime.toLocalDate());
        return yearMonthFromQuery.equals(searchDate);
    }
}

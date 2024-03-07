package Company.demo.commute.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
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
    private boolean isWorking;

    public CommuteHistory() {
    }

    public CommuteHistory(Employee employee) {
        this.employee = employee;
    }


    public void startWorking(){
        this.startTime = LocalDateTime.now();
        this.isWorking = true;
    }

    public void endWorking(){
        this.endTime = LocalDateTime.now();
        this.isWorking = false;
    }

}

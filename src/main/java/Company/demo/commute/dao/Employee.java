package Company.demo.commute.dao;

import Company.demo.commute.dto.request.MemberSaveRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Entity
@Getter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer annualLeave;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "employee")
    private List<CommuteHistory> commuteHistories;

    @OneToMany(mappedBy = "employee")
    private List<AnnualLeaveRegister> annualLeaveRegisters;

    private String startDate;
    private String birthDay;


    public Employee() {
    }

    public Employee(MemberSaveRequest request, Position position) {
        this.name = request.getName();
        this.position = position;
        this.startDate = request.getStartDate();
        this.birthDay = request.getBirthDay();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Optional<CommuteHistory> findCommuteHistory() {
        return commuteHistories.stream()
                .filter(target -> target.getStartTime().toLocalDate().equals(LocalDateTime.now().toLocalDate()))
                .findFirst();
    }

//    @PrePersist
//    @PreUpdate
//    public void updateAnnualLeave() {
//        int currentYear = Year.now().getValue();
//        int startYear = Integer.parseInt(startDate.split("-")[0]);
//        if (currentYear == startYear) {
//            annualLeave = 11;
//        } else {
//            annualLeave = 15;
//        }
//    }

    public void useAL(Integer al) {
        this.annualLeave -= al;
    }

}

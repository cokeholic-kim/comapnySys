package Company.demo.commute.dao;

import Company.demo.commute.dto.request.MemberSaveRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    private LocalDate startDate;
    private LocalDate birthDay;
    private Boolean isManager;

    public Employee(MemberSaveRequest request) {
        this.name = request.getName();
        this.startDate = request.getStartDate();
        this.birthDay = request.getBirthDay();
        this.isManager = request.getIsManager();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void useAL(Integer al) {
        this.annualLeave -= al;
    }

    public void setAnnualLeave(int annualLeave) {
        this.annualLeave = annualLeave;
    }
}

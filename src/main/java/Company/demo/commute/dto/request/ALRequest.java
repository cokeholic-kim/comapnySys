package Company.demo.commute.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ALRequest {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
}

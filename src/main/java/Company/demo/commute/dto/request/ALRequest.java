package Company.demo.commute.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ALRequest {
    private Long employeeId;
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;

    @AssertTrue(message = "endDate는 startDate보다 이후여야 합니다.")
    public boolean isDateCheck() {
        if (startDate.isBefore(endDate)) {
            return true;
        }
        return false;
    }
}

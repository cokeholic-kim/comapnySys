package Company.demo.commute.dto.request;

import java.time.YearMonth;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CommuteGetDto {
    private Long employeeId;
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth searchDate;
}

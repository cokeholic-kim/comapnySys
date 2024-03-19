package Company.demo.commute.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommuteTimeResponse {
    private String date;
    private Integer workingMinutes;
    private boolean usingDayOff;

    public CommuteTimeResponse(LocalDate currentDate) {
        this.date = String.valueOf(currentDate);
        this.workingMinutes = 0;
        this.usingDayOff = true;
    }
}

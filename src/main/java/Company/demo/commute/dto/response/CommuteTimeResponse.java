package Company.demo.commute.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommuteTimeResponse {
    private String date;
    private Integer workingMinutes;
    private boolean usingDayOff;
}

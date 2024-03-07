package Company.demo.commute.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverTimeRequest {
    private String yearMonth;
    private Integer holidays;
}

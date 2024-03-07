package Company.demo.commute.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverTimeResponse {
    private Integer id;
    private String name;
    private Integer overtimeMinutes;
}

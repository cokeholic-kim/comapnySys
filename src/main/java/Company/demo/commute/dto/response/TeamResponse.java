package Company.demo.commute.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponse {
    private String name;
    private String manager;
    private Long count;
}

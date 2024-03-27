package Company.demo.commute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OverTimeResponse {
    private Integer id;
    private String name;
    private Integer overtimeMinutes;
}

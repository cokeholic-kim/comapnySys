package Company.demo.commute.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommuteTotalResponse {
    private List<CommuteTimeResponse> detail = new ArrayList<>();
    private Integer sum;

    public CommuteTotalResponse(List<CommuteTimeResponse> detail) {
        this.detail = detail;
        this.sum = detail.stream().mapToInt(CommuteTimeResponse::getWorkingMinutes).sum();
    }
}

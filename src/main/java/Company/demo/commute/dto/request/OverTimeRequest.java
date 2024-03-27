package Company.demo.commute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
public class OverTimeRequest {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM")
    //TODO 해당월의 최대일자를 넘지않게 추가.
    private YearMonth yearMonth;
    @NotNull
    private Integer holidays;
}

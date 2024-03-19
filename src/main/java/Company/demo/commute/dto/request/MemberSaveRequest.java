package Company.demo.commute.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MemberSaveRequest {
    @NotBlank(message = "이름을 알려주세요")
    private String name;
    @NotNull(message = "팀장인지 아닌지 알려주세요")
    private Boolean isManager;
    @NotNull(message = "입사일 정보가 필요합니다")
    private LocalDate startDate;
    @NotNull(message = "생일 정보가 필요합니다")
    private LocalDate birthDay;
}

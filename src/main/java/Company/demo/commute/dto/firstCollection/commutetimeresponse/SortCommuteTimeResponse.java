package Company.demo.commute.dto.firstCollection.commutetimeresponse;

import Company.demo.commute.dto.response.CommuteTimeResponse;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class SortCommuteTimeResponse {
    private List<CommuteTimeResponse> commuteTimeResponseList;

    public SortCommuteTimeResponse(List<CommuteTimeResponse> commuteTimeResponseList) {
        this.commuteTimeResponseList = commuteTimeResponseList;
    }

    public List<CommuteTimeResponse> sort(){
        return commuteTimeResponseList.stream()
                .sorted(Comparator.comparing(response -> LocalDate.parse(response.getDate())))
                .toList();
    }
}

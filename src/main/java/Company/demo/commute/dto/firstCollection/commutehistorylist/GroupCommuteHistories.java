package Company.demo.commute.dto.firstCollection.commutehistorylist;

import Company.demo.commute.dao.CommuteHistory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCommuteHistories {

    private List<CommuteHistory> commuteHistories;

    public GroupCommuteHistories(List<CommuteHistory> commuteHistories) {
        this.commuteHistories = commuteHistories;
    }

    public Map<String, Integer> groupByDate() {
        return commuteHistories.stream().collect(Collectors.groupingBy(
                result -> dateTimeToFormatString(result.getStartTime(), "yyyy-MM-dd"),
                Collectors.summingInt(
                        result -> (int) Duration.between(result.getStartTime(), result.getEndTime()).toMinutes())
        ));
    }

    private String dateTimeToFormatString(LocalDateTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}

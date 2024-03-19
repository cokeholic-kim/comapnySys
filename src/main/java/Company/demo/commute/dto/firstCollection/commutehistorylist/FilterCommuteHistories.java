package Company.demo.commute.dto.firstCollection.commutehistorylist;

import Company.demo.commute.dao.CommuteHistory;
import java.time.YearMonth;
import java.util.List;

public class FilterCommuteHistories {
    private List<CommuteHistory> commuteHistories;

    public FilterCommuteHistories(List<CommuteHistory> commuteHistories) {
        //생성시 근무중인데이터 제거
        this.commuteHistories = commuteHistories.stream().filter(it->!it.getIsWorking()).toList();
    }

    public List<CommuteHistory> filterByDate(YearMonth yearMonth) {
        //검색 연월이 같고 근무중이 아닌자료 출퇴근기록을 필터링
        return commuteHistories.stream().filter(it->it.isSameYearMonth(yearMonth)).toList();
    }
}

package Company.demo.commute.dto.firstCollection.commutetimeresponse;

import Company.demo.commute.dao.AnnualLeaveRegister;
import Company.demo.commute.dao.CommuteHistory;
import Company.demo.commute.dao.Employee;
import Company.demo.commute.dto.firstCollection.annualleavelist.AnnualLeaveToCommuteResponse;
import Company.demo.commute.dto.firstCollection.annualleavelist.FilterAnnualLeaveRegistered;
import Company.demo.commute.dto.firstCollection.commutehistorylist.FilterCommuteHistories;
import Company.demo.commute.dto.firstCollection.commutehistorylist.GroupCommuteHistories;
import Company.demo.commute.dto.request.CommuteGetDto;
import Company.demo.commute.dto.response.CommuteTimeResponse;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommuteTimeResponseList {
    List<CommuteTimeResponse> commuteTimeResponseList;

    public CommuteTimeResponseList(CommuteGetDto request, Employee employee) {
        List<CommuteHistory> filtered = new FilterCommuteHistories(employee.getCommuteHistories())
                .filterByDate(request.getSearchDate());
        Map<String, Integer> grouped = new GroupCommuteHistories(filtered).groupByDate();
        List<CommuteTimeResponse> commuteTimeResponsesByCommuteHistories = mapToCommuteTimeResponse(grouped);

        //연차 정보를 더해준다.
        List<CommuteTimeResponse> commuteTimeResponsesByAnnualLeave = AnnualLeaveToCommuteTimeResponse(
                request.getSearchDate(), employee);
        commuteTimeResponsesByCommuteHistories.addAll(commuteTimeResponsesByAnnualLeave);

        //정렬
        this.commuteTimeResponseList = new SortCommuteTimeResponse(commuteTimeResponsesByCommuteHistories).sort();
    }

    private static List<CommuteTimeResponse> AnnualLeaveToCommuteTimeResponse(YearMonth searchYearMonth,
                                                                              Employee employee) {
        List<AnnualLeaveRegister> filter = new FilterAnnualLeaveRegistered(employee.getAnnualLeaveRegisters()).filter(
                searchYearMonth);
        return new AnnualLeaveToCommuteResponse(filter).leaveToResponse(searchYearMonth);
    }

    private List<CommuteTimeResponse> mapToCommuteTimeResponse(Map<String, Integer> groupedCommuteHistory) {
        return groupedCommuteHistory.entrySet().stream()
                .map(result -> {
                    CommuteTimeResponse response = new CommuteTimeResponse();
                    response.setDate(result.getKey());
                    response.setWorkingMinutes(result.getValue());
                    return response;
                }).collect(Collectors.toList());
    }
}

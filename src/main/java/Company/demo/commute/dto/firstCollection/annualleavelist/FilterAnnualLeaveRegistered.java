package Company.demo.commute.dto.firstCollection.annualleavelist;

import Company.demo.commute.dao.AnnualLeaveRegister;
import java.time.YearMonth;
import java.util.List;

public class FilterAnnualLeaveRegistered {
    private List<AnnualLeaveRegister> annualLeaveRegisters;

    public FilterAnnualLeaveRegistered(List<AnnualLeaveRegister> annualLeaveRegisters) {
        this.annualLeaveRegisters = annualLeaveRegisters;
    }

    public List<AnnualLeaveRegister> filter(YearMonth yearMonth){
        return annualLeaveRegisters.stream().filter(leave -> {
            YearMonth startYearMonth = YearMonth.from(leave.getStartDate());
            YearMonth endYearMonth = YearMonth.from(leave.getEndDate());
            return !startYearMonth.isAfter(yearMonth) && !endYearMonth.isBefore(yearMonth);
        }).toList();
    }
}

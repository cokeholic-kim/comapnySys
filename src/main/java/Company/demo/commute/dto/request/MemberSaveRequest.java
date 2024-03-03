package Company.demo.commute.dto.request;

import Company.demo.commute.dao.Position;

public class MemberSaveRequest {
    private String name;
    private String positionName;
    private String startDate;
    private String birthDay;

    public String getName() {
        return name;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getBirthDay() {
        return birthDay;
    }
}

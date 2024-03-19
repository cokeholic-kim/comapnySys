package Company.demo.commute.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String name;
    private String teamName;
    private String role;
    private String birthDay;
    private String WorkStartDate;

    public void setRole(Boolean isManager){
        if(isManager){
            this.role = "Manager";
        }else{
            this.role = "MEMBER";
        }
    }
}

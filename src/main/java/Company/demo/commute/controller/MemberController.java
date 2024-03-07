package Company.demo.commute.controller;

import Company.demo.commute.dto.request.ALRequest;
import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.request.OverTimeRequest;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.dto.response.OverTimeResponse;
import Company.demo.commute.service.ExcelService;
import Company.demo.commute.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;
    private final ExcelService excelService;

    @PostMapping
    public void saveMember(@RequestBody MemberSaveRequest request) {
        service.saveMember(request);
    }

    @PostMapping("/setTeam")
    public void setTeam(@RequestBody MemberTeamSetDto request) {
        service.setTeam(request);
    }

    @GetMapping
    public List<EmployeeResponse> getEmployee() {
        return service.getEmployee();
    }

    @PostMapping("/register/annualLeave")
    public void registerAL(@RequestBody ALRequest request) {
        service.registerAl(request);
    }

    @GetMapping("/annualLeave")
    public Integer getAnnualLeave(Integer employeeId) {
        return service.getAnnualLeave(employeeId);
    }

    @PostMapping("/overTimeCalc")
    public List<OverTimeResponse> getOverTime(@RequestBody OverTimeRequest overTimeRequest) {
        return service.getOverTime(overTimeRequest.getYearMonth(), overTimeRequest.getHolidays());
    }

    @GetMapping("/overTimeExcel")
    public void getExcel(Integer holidays, HttpServletResponse response) {
        excelService.getExcel(holidays, response);
    }
}

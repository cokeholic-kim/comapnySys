package Company.demo.commute.controller;

import Company.demo.commute.dto.request.MemberPositionSetDto;
import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.request.OverTimeRequest;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.dto.response.OverTimeResponse;
import Company.demo.commute.service.ExcelService;
import Company.demo.commute.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    public void saveMember(@Valid @RequestBody MemberSaveRequest request) {
        service.saveMember(request);
    }

    @PostMapping("/setTeam")
    public void setTeam(@RequestBody MemberTeamSetDto request) {
        service.setTeam(request);
    }

    @PostMapping("/setPosition")
    public void setPosition(@RequestBody MemberPositionSetDto request) {
        service.setPosition(request);
    }

    @GetMapping
    public List<EmployeeResponse> getEmployee() {
        return service.getEmployee();
    }

    @GetMapping("/overTimeCalc")
    public List<OverTimeResponse> getOverTime(@Validated OverTimeRequest overTimeRequest) {
        return service.getOverTime(overTimeRequest);
    }

    @GetMapping("/overTimeExcel")
    public void getExcel(Integer holidays, HttpServletResponse response) {
        excelService.getMemberExcel(holidays, response);
    }
}

package Company.demo.commute.controller;

import Company.demo.commute.dto.request.MemberSaveRequest;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.response.EmployeeResponse;
import Company.demo.commute.service.MemberService;
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

    @PostMapping
    public void saveMember(@RequestBody MemberSaveRequest request) {
        service.saveMember(request);
    }

    @PostMapping("/setTeam")
    public void setTeam(@RequestBody MemberTeamSetDto request){
        service.setTeam(request);
    }

    @GetMapping
    public List<EmployeeResponse> getEmployee(){
        return service.getEmployee();
    }
}

package Company.demo.commute.controller;

import Company.demo.commute.dao.Team;
import Company.demo.commute.dto.request.MemberTeamSetDto;
import Company.demo.commute.dto.request.TeamSetDto;
import Company.demo.commute.dto.response.TeamResponse;
import Company.demo.commute.service.TeamService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping
    public void registerTeam(@RequestBody Map<String, String> requestJson){
        service.registerTeam(requestJson.get("teamName"));
    }

    @PostMapping("/setLeader")
    public void setTeamLeader(@RequestBody TeamSetDto request){
        service.registerTeamLeader(request);
    }

    @GetMapping
    public List<TeamResponse> getTeam(){
        return service.getTeam();
    }
}

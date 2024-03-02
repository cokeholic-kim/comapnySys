package Company.demo.commute.controller;

import Company.demo.commute.service.TeamService;
import java.util.Map;
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


}

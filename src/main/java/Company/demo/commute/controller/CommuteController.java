package Company.demo.commute.controller;

import Company.demo.commute.dto.request.CommuteGetDto;
import Company.demo.commute.dto.response.CommuteTotalResponse;
import Company.demo.commute.service.CommuteService;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commute")
public class CommuteController {
    private final CommuteService service;

    public CommuteController(CommuteService service) {
        this.service = service;
    }

    @PostMapping
    public void checkWork(@RequestBody Map<String, Long> json) {
        service.startWork(json.get("employeeId"));
    }

    @PostMapping("/worktime")
    public CommuteTotalResponse checkTime(@RequestBody CommuteGetDto request) {
        return service.checkTime(request);
    }

    @PostMapping("/worktime2")
    public CommuteTotalResponse checkTime2(@RequestBody CommuteGetDto request) {
        return service.checkTime2(request);
    }
}

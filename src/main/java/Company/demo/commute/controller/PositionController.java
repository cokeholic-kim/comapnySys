package Company.demo.commute.controller;

import Company.demo.commute.service.PositionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService service;

    @PostMapping
    public void savePosition(@RequestBody Map<String, String> Json) {
        String positionName = Json.get("positionName");
        service.savePosition(positionName);
    }
}

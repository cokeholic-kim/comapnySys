package Company.demo.commute.controller;

import Company.demo.commute.dto.request.ALRequest;
import Company.demo.commute.dto.response.ResponseAnnualLeave;
import Company.demo.commute.service.AnnualLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/annual-leave")
public class AnnualLeaveController {

    private final AnnualLeaveService service;

    @PutMapping("/set-annual-leave")
    public void setAnnualLeave() {
        service.setAnnualLeave();
    }

    @PostMapping("/register")
    public void registerAL(@Valid @RequestBody ALRequest request) {
        service.registerAl(request);
    }

    @GetMapping
    public ResponseAnnualLeave getAnnualLeave(Integer employeeId) {
        return service.getAnnualLeave(employeeId);
    }
}

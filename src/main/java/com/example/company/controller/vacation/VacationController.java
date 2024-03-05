package com.example.company.controller.vacation;

import com.example.company.dto.vacation.request.VacationRegisterRequest;
import com.example.company.service.vacation.VacationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @PostMapping("/vacation/register")
    public void registerVacation(@RequestBody VacationRegisterRequest request) {
        vacationService.registerVacation(request);
    }


}

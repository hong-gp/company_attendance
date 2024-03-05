package com.example.company.dto.vacation.request;

import java.time.LocalDate;

public class VacationRegisterRequest {

    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;

    public VacationRegisterRequest(Long employeeId, LocalDate startDate, LocalDate endDate) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

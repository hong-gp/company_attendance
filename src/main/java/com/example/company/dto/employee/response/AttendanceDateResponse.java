package com.example.company.dto.employee.response;

import java.time.LocalDate;

public class AttendanceDateResponse {

    private LocalDate date;
    private Long workingMinutes;
    private boolean usingVacation;

    public AttendanceDateResponse(LocalDate date, Long workingMinutes, boolean usingVacation) {
        this.date = date;
        this.workingMinutes = workingMinutes;
        this.usingVacation = usingVacation;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getWorkingMinutes() {
        return workingMinutes;
    }

    public boolean isUsingVacation() {
        return usingVacation;
    }
}

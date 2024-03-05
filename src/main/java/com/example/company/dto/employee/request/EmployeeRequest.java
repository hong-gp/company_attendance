package com.example.company.dto.employee.request;

import com.example.company.domain.employee.Employee;
import com.example.company.domain.employee.EmployeeRole;

import java.time.LocalDate;

public class EmployeeRequest {

    private String name;
    private String teamName;
    private EmployeeRole role;
    private LocalDate birthday;
    private LocalDate workStartDate;

    public EmployeeRequest(String name, String teamName, EmployeeRole role, LocalDate birthday, LocalDate workStartDate) {
        this.name = name;
        this.teamName = teamName;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
    }

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate getWorkStartDate() {
        return workStartDate;
    }
}

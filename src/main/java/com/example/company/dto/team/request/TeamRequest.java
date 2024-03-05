package com.example.company.dto.team.request;

public class TeamRequest {

    private String name;
    private Integer vacationDeadline;

    protected TeamRequest() {}

    public TeamRequest(String name, Integer vacationDeadline) {
        this.name = name;
        this.vacationDeadline = vacationDeadline;
    }

    public String getName() {
        return name;
    }

    public Integer getVacationDeadline() {
        return vacationDeadline;
    }
}

package com.example.company.service.team;

import com.example.company.domain.employee.EmployeeRepository;
import com.example.company.domain.employee.EmployeeRole;
import com.example.company.domain.team.Team;
import com.example.company.domain.team.TeamRepository;
import com.example.company.dto.team.request.TeamRequest;
import com.example.company.dto.team.response.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    public TeamService(TeamRepository teamRepository, EmployeeRepository employeeRepository) {
        this.teamRepository = teamRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void registerTeam(TeamRequest request) {
        teamRepository.save(new Team(request.getName(), request.getVacationDeadline()));
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamResponse> responses = new ArrayList<>();

        for (Team team : teams) {
            String managerName = employeeRepository.findByTeamAndRole(team, EmployeeRole.MANAGER)
                    .orElse(null).getName();
            responses.add(new TeamResponse(team, managerName));
        }
        return responses;
//        return teams.stream().map(team -> new TeamResponse(team, employeeRepository.findByTeamAndRole(team, EmployeeRole.MANAGER).orElse(null).getName())).collect(Collectors.toList());
    }
}

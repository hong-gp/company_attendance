package com.example.company.controller.team;

import com.example.company.dto.team.request.TeamRequest;
import com.example.company.dto.team.response.TeamResponse;
import com.example.company.service.team.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/team/register")
    public void registerTeam(@RequestBody TeamRequest request) {
        teamService.registerTeam(request);
    }

    @GetMapping("/team/list")
    public List<TeamResponse> getAllTeams() {
        return teamService.getAllTeams();
    }
}

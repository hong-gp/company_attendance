package com.example.company.domain.employee;

import com.example.company.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByTeamAndRole(Team team, EmployeeRole role);
}

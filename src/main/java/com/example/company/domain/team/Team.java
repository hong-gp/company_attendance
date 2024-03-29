package com.example.company.domain.team;

import com.example.company.domain.employee.Employee;
import com.example.company.domain.employee.EmployeeRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    @Column
    private Long memberCount;

    @Column
    private Integer vacationDeadline;

    @OneToMany
    List<Employee> employees;

    public Team(String name, Integer vacationDeadline) {
        this.name = name;
        this.memberCount = 0L;
        this.vacationDeadline = vacationDeadline;
    }

    public void incrementMemberCount() {
        this.memberCount += 1;
    }
}

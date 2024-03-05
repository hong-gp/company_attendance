package com.example.company.domain.employee;

import com.example.company.domain.employee.attendance.AttendanceRecord;
import com.example.company.domain.team.Team;
import com.example.company.domain.vacation.Vacation;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private EmployeeRole role;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private LocalDate workStartDate;

    @Column
    private Long vacationCount;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacation> vacations = new ArrayList<>();

    protected Employee() {}

    public Employee(String name, Team team, EmployeeRole role, LocalDate birthday, LocalDate workStartDate, Long vacationCount) {
        this.name = name;
        this.team = team;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
        this.vacationCount = vacationCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
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

    public Long getVacationCount() {
        return vacationCount;
    }

    public void startWork() {
        this.attendanceRecords.add(new AttendanceRecord(this));
    }

    public void endWork() {
        List<AttendanceRecord> attendanceRecords = this.attendanceRecords;
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getWorkDate().isEqual(LocalDate.now())) {
                record.endWork();
                return;
            }
        }
    }

    public void assignManager() {
        this.role = EmployeeRole.MANAGER;
    }

    public void assignMember() {
        this.role = EmployeeRole.MEMBER;
    }

    public void minusVacationCount(long count) {
        if (this.vacationCount - count >= 0) {
            this.vacationCount -= count;
        } else {
            throw new IllegalArgumentException("연차가 부족합니다.");
        }
    }

}

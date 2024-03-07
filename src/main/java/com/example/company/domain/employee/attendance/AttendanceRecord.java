package com.example.company.domain.employee.attendance;

import com.example.company.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDate workDate;

    private LocalTime workStart;

    private LocalTime workEnd;

    public AttendanceRecord(Employee employee) {
        this.employee = employee;
        this.workDate = LocalDate.now();
        this.workStart = LocalTime.now();
    }

    public void endWork() {
        this.workEnd = LocalTime.now().withNano(0);
    }

    public long workingMinutes() {
        return Duration.between(this.getWorkStart(), this.getWorkEnd()).toMinutes();
    }
}

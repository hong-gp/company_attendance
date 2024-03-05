package com.example.company.domain.employee.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    @Query("select a from AttendanceRecord a where a.employee.id = ?1 and year(a.workDate) = ?2 and month(a.workDate) = ?3")
    Optional<List<AttendanceRecord>> findAllAttendanceDetail(Long employeeId, int year, int month);

}
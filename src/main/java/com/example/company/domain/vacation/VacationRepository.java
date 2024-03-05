package com.example.company.domain.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("select v from Vacation v where v.employee.id = ?1 and year(v.startDate) = ?2")
    Optional<List<Vacation>> findVacationEmployeeForYear(Long employeeId, int year);

    @Query("select count(v) > 0 from Vacation v where v.employee.id = ?1 and ?2 between v.startDate and v.endDate")
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}

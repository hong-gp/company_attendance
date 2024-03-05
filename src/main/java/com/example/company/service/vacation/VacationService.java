package com.example.company.service.vacation;

import com.example.company.domain.employee.Employee;
import com.example.company.domain.employee.EmployeeRepository;
import com.example.company.domain.vacation.Vacation;
import com.example.company.domain.vacation.VacationRepository;
import com.example.company.dto.vacation.request.VacationRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;

    public VacationService(VacationRepository vacationRepository, EmployeeRepository employeeRepository) {
        this.vacationRepository = vacationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void registerVacation(VacationRegisterRequest request) {
        // 직원 존재 여부 확인
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 직원입니다.");
                });

        // 휴가 신청일이 팀의 휴가 신청 마감일을 만족하는지
        // 휴가 시작일 - 데드라인 > 현재날짜
        LocalDate minusDays = request.getStartDate().minusDays(employee.getTeam().getVacationDeadline());
        if (!LocalDate.now().isBefore(minusDays)) {
            throw new IllegalArgumentException("마감일이 지났습니다.");
        }

        // 직원의 연차 개수 빼기
        employee.minusVacationCount(request.getStartDate().until(request.getEndDate(), ChronoUnit.DAYS) + 1);

        // 휴가를 신청한 날짜가 이미 있을 때
        if (vacationRepository.existsByEmployeeIdAndDate(employee.getId(), request.getStartDate())
                || vacationRepository.existsByEmployeeIdAndDate(employee.getId(), request.getEndDate())) {
            throw new IllegalArgumentException("이미 휴가를 신청한 날짜입니다.");
        }

        // 휴가 기록 저장
        vacationRepository.save(new Vacation(employee, request.getStartDate(), request.getEndDate()));
    }
}

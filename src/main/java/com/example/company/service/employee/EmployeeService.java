package com.example.company.service.employee;

import com.example.company.domain.employee.Employee;
import com.example.company.domain.employee.EmployeeRepository;
import com.example.company.domain.employee.EmployeeRole;
import com.example.company.domain.employee.attendance.AttendanceRecord;
import com.example.company.domain.employee.attendance.AttendanceRecordRepository;
import com.example.company.domain.team.Team;
import com.example.company.domain.team.TeamRepository;
import com.example.company.domain.vacation.VacationRepository;
import com.example.company.dto.employee.request.EmployeeRequest;
import com.example.company.dto.employee.request.EmployeeWorkEndRequest;
import com.example.company.dto.employee.request.EmployeeWorkStartRequest;
import com.example.company.dto.employee.response.AttendanceDateResponse;
import com.example.company.dto.employee.response.AttendanceDetailResponse;
import com.example.company.dto.employee.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final TeamRepository teamRepository;
    private final VacationRepository vacationRepository;

    @Transactional
    public void registerEmployee(EmployeeRequest request) {
        // 팀 이름으로 팀 찾기
        Team team = teamRepository.findByName(request.getTeamName())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않은 팀 입니다.");
                });

        // 팀 매니저 설정
        // 기존의 매니저가 존재하면 기존의 매니저를 MEMBER 로 바꿈
        if (request.getRole() == EmployeeRole.MANAGER) {
            employeeRepository.findByTeamAndRole(team, EmployeeRole.MANAGER)
                    .ifPresent(oldManager -> {
                        oldManager.assignMember();
                    });
        }

        // 직원 등록
        Employee employee = new Employee(
                request.getName(),
                team,
                request.getRole(),
                request.getBirthday(),
                request.getWorkStartDate(),
                request.getWorkStartDate().getYear() == LocalDate.now().getYear() ? 11L : 15L
        );
        employeeRepository.save(employee);

        // 팀의 member_count 증가
        team.incrementMemberCount();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 직원입니다.");
        });
        return new EmployeeResponse(employee);
    }

    @Transactional
    public void startWork(EmployeeWorkStartRequest request) {
        // 등록되지 않은 직원이 출근하려는 경우
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("등록되지 않은 직원입니다.");
                });

        // 출근한 직원이 또 다시 출근하려는 경우
        // 그 날, 출근했다 퇴근한 직원이 다시 출근하려는 경우
        attendanceRecordRepository.findByEmployeeIdAndWorkDate(employee.getId(), LocalDate.now())
                .ifPresent(record -> {
                    throw new IllegalArgumentException("이미 오늘 출근한 직원입니다.");
                });

        employee.startWork();
    }

    @Transactional
    public void endWork(EmployeeWorkEndRequest request) {
        // 등록되지 않은 직원이 퇴근하려는 경우
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(IllegalArgumentException::new);

        // 퇴근한 직원이 또 다시 퇴근하려는 경우
        AttendanceRecord record = attendanceRecordRepository.findByEmployeeIdAndWorkDate(employee.getId(), LocalDate.now())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("출근 기록이 없습니다.");
                });
        if (record.getWorkEnd() != null) {
            throw new IllegalArgumentException("이미 퇴근한 직원입니다.");
        }

        // 퇴근하려는 직원이 출근하지 않았던 경우
        // 해당 직원의 오늘 날짜 출근 기록 찾기
        employee.endWork();
    }

    @Transactional
    public AttendanceDetailResponse getAttendanceDetail(Long employeeId, YearMonth date) {
        // 등록 되지 않은 직원인 경우
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(IllegalArgumentException::new);

        // 직원 ID와 연/월을 통해 출근 기록 조회
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findAllAttendanceDetail(
                        employee.getId(),
                        date.getYear(),
                        date.getMonthValue())
                .orElseThrow(IllegalArgumentException::new);

        // n월의 1일부터 마지막날까지 출근 기록 조회
        // 해당 날짜의 휴가 기록이 있으면 true, 아니면 false
        List<AttendanceDateResponse> workDate = new ArrayList<>();
        Long sum = 0L;
        for (int day = 1; day <= date.lengthOfMonth(); day++) {
            // 1일부터 마지막날까지 날짜를 받는 변수
            LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), day);
            AttendanceRecord record = attendanceRecordRepository.findByEmployeeIdAndWorkDate(employeeId, localDate).orElse(null);
            long workingMinutes = record == null ? 0 : record.workingMinutes();

            workDate.add(new AttendanceDateResponse(localDate, workingMinutes, vacationRepository.existsByEmployeeIdAndDate(employeeId, localDate)));
            sum += workingMinutes;
        }
        return new AttendanceDetailResponse(workDate, sum);
    }
}

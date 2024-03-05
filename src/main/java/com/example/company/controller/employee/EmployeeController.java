package com.example.company.controller.employee;

import com.example.company.domain.employee.Employee;
import com.example.company.dto.employee.request.EmployeeRequest;
import com.example.company.dto.employee.request.EmployeeWorkEndRequest;
import com.example.company.dto.employee.request.EmployeeWorkStartRequest;
import com.example.company.dto.employee.response.AttendanceDetailResponse;
import com.example.company.dto.employee.response.EmployeeResponse;
import com.example.company.service.employee.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee/register")
    public void registerEmployee(@RequestBody EmployeeRequest request) {
        employeeService.registerEmployee(request);
    }

    @GetMapping("/employee/list")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/read")
    public EmployeeResponse readEmployee(Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employee/work/start")
    public void goToWork(@RequestBody EmployeeWorkStartRequest request) {
        employeeService.startWork(request);
    }

    @PutMapping("/employee/work/end")
    public void getOffWork(@RequestBody EmployeeWorkEndRequest request) {
        employeeService.endWork(request);
    }

    @GetMapping ("/employee/work/detail")
    public AttendanceDetailResponse employeeWorkDetail(@RequestParam Long id, @RequestParam YearMonth date) {
        return employeeService.getAttendanceDetail(id, date);
    }
}

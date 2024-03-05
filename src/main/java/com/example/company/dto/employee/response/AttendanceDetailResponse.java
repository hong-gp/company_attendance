package com.example.company.dto.employee.response;

import java.util.List;

public class AttendanceDetailResponse {

    private List<AttendanceDateResponse> detail;
    private Long sum;

    public AttendanceDetailResponse(List<AttendanceDateResponse> detail, Long sum) {
        this.detail = detail;
        this.sum = sum;
    }

    public List<AttendanceDateResponse> getDetail() {
        return detail;
    }

    public Long getSum() {
        return sum;
    }
}

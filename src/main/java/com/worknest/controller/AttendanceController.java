package com.worknest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worknest.dto.AttendanceReportDto;
import com.worknest.dto.PunchInDto;
import com.worknest.dto.PunchOutDto;
import com.worknest.entity.Attendance;
import com.worknest.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/punch-in")
    public Attendance punchIn(@RequestBody PunchInDto dto) {
        return attendanceService.punchIn(dto);
    }

    @PostMapping("/punch-out")
    public Attendance punchOut(@RequestBody PunchOutDto dto) {
        return attendanceService.punchOut(dto);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getEmployeeAttendance(@PathVariable Long employeeId) {
        return attendanceService.getEmployeeAttendance(employeeId);
    }
    
    @GetMapping("/employee/{employeeId}/monthly-report")
    public AttendanceReportDto getMonthlyReport(
            @PathVariable Long employeeId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return attendanceService.getMonthlyReport(employeeId, year, month);
    }
}
package com.worknest.controller;

import com.worknest.dto.PunchInDto;
import com.worknest.dto.PunchOutDto;
import com.worknest.entity.Attendance;
import com.worknest.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
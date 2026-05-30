package com.worknest.service;

import com.worknest.dto.PunchInDto;
import com.worknest.dto.PunchOutDto;
import com.worknest.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    Attendance punchIn(PunchInDto dto);

    Attendance punchOut(PunchOutDto dto);

    List<Attendance> getEmployeeAttendance(Long employeeId);
}
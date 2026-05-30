package com.worknest.service;

import com.worknest.dto.PunchInDto;
import com.worknest.dto.PunchOutDto;
import com.worknest.entity.Attendance;
import com.worknest.entity.Employee;
import com.worknest.repository.AttendanceRepository;
import com.worknest.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Attendance punchIn(PunchInDto dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        if (attendanceRepository.findByEmployeeAndAttendanceDate(employee, today).isPresent()) {
            throw new RuntimeException("Employee already punched in today");
        }

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("PUNCHED_IN");

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance punchOut(PunchOutDto dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeAndAttendanceDate(employee, today)
                .orElseThrow(() -> new RuntimeException("Punch in record not found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Employee already punched out today");
        }

        LocalTime checkOutTime = LocalTime.now();

        attendance.setCheckOutTime(checkOutTime);

        double workingHours = Duration.between(
                attendance.getCheckInTime(),
                checkOutTime
        ).toMinutes() / 60.0;

        attendance.setWorkingHours(workingHours);

        String status;

        if (workingHours >= 8) {
            status = "PRESENT";
        } else if (workingHours >= 4) {
            status = "HALF_DAY";
        } else {
            status = "ABSENT";
        }

        if (attendance.getCheckInTime().isAfter(LocalTime.of(10, 0))
                && status.equals("PRESENT")) {
            status = "PRESENT_LATE";
        }

        attendance.setStatus(status);

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getEmployeeAttendance(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return attendanceRepository.findByEmployee(employee);
    }
}
package com.worknest.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.worknest.dto.AttendanceReportDto;
import com.worknest.dto.PunchInDto;
import com.worknest.dto.PunchOutDto;
import com.worknest.entity.Attendance;
import com.worknest.entity.Employee;
import com.worknest.repository.AttendanceRepository;
import com.worknest.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

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
    
    @Override
    public AttendanceReportDto getMonthlyReport(Long employeeId, int year, int month) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Attendance> attendanceList =
                attendanceRepository.findByEmployeeAndAttendanceDateBetween(
                        employee,
                        startDate,
                        endDate
                );

        int presentDays = 0;
        int halfDays = 0;
        int absentDays = 0;
        int lateDays = 0;
        double totalWorkingHours = 0;

        for (Attendance attendance : attendanceList) {

            String status = attendance.getStatus();

            if ("PRESENT".equals(status)) {
                presentDays++;
            } else if ("PRESENT_LATE".equals(status)) {
                presentDays++;
                lateDays++;
            } else if ("HALF_DAY".equals(status)) {
                halfDays++;
            } else if ("ABSENT".equals(status)) {
                absentDays++;
            }

            if (attendance.getWorkingHours() != null) {
                totalWorkingHours += attendance.getWorkingHours();
            }
        }

        return AttendanceReportDto.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getFullName())
                .totalDays(attendanceList.size())
                .presentDays(presentDays)
                .halfDays(halfDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .totalWorkingHours(totalWorkingHours)
                .build();
    }
}
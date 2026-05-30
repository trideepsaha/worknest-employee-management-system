package com.worknest.repository;

import com.worknest.entity.Attendance;
import com.worknest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate attendanceDate
    );

    List<Attendance> findByEmployee(Employee employee);
}
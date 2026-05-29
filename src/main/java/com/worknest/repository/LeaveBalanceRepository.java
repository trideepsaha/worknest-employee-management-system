package com.worknest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worknest.entity.Employee;
import com.worknest.entity.LeaveBalance;
import com.worknest.entity.LeaveType;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
	Optional<LeaveBalance> findByEmployeeAndLeaveType(Employee employee, LeaveType leaveType);

    List<LeaveBalance> findByEmployee(Employee employee);
}
package com.worknest.repository;

import com.worknest.entity.Employee;
import com.worknest.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee(Employee employee);

    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByEmployee_ManagerAndStatus(Employee manager, String status);
}
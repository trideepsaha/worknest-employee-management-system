package com.worknest.service;

import com.worknest.dto.LeaveActionDto;
import com.worknest.dto.LeaveApplyDto;
import com.worknest.entity.Employee;
import com.worknest.entity.LeaveBalance;
import com.worknest.entity.LeaveRequest;
import com.worknest.entity.LeaveType;
import com.worknest.repository.EmployeeRepository;
import com.worknest.repository.LeaveBalanceRepository;
import com.worknest.repository.LeaveRequestRepository;
import com.worknest.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveRequest applyLeave(LeaveApplyDto dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new RuntimeException("End date cannot be before start date");
        }

        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeAndLeaveType(employee, leaveType)
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        if (balance.getRemainingLeaves() < days) {
            throw new RuntimeException("Insufficient leave balance");
        }

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .leaveType(leaveType)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .reason(dto.getReason())
                .status("PENDING")
                .build();

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest approveLeave(Long leaveRequestId, LeaveActionDto dto) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (!leaveRequest.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending leave can be approved");
        }

        Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Employee employee = leaveRequest.getEmployee();

        if (employee.getManager() == null || !employee.getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("This manager is not authorized to approve this leave");
        }

        long days = ChronoUnit.DAYS.between(
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate()
        ) + 1;

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeAndLeaveType(employee, leaveRequest.getLeaveType())
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        if (balance.getRemainingLeaves() < days) {
            throw new RuntimeException("Insufficient leave balance");
        }

        balance.setUsedLeaves(balance.getUsedLeaves() + (int) days);
        balance.setRemainingLeaves(balance.getRemainingLeaves() - (int) days);

        leaveBalanceRepository.save(balance);

        leaveRequest.setStatus("APPROVED");
        leaveRequest.setApprovedBy(manager);
        leaveRequest.setRejectionReason(null);

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest rejectLeave(Long leaveRequestId, LeaveActionDto dto) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (!leaveRequest.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending leave can be rejected");
        }

        Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Employee employee = leaveRequest.getEmployee();

        if (employee.getManager() == null || !employee.getManager().getId().equals(manager.getId())) {
            throw new RuntimeException("This manager is not authorized to reject this leave");
        }

        leaveRequest.setStatus("REJECTED");
        leaveRequest.setApprovedBy(manager);
        leaveRequest.setRejectionReason(dto.getRejectionReason());

        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getEmployeeLeaveHistory(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return leaveRequestRepository.findByEmployee(employee);
    }

    @Override
    public List<LeaveRequest> getPendingLeavesForManager(Long managerId) {

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return leaveRequestRepository.findByEmployee_ManagerAndStatus(manager, "PENDING");
    }

    @Override
    public List<LeaveBalance> getEmployeeLeaveBalance(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return leaveBalanceRepository.findByEmployee(employee);
    }
}	
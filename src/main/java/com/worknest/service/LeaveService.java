package com.worknest.service;

import com.worknest.dto.LeaveActionDto;
import com.worknest.dto.LeaveApplyDto;
import com.worknest.entity.LeaveBalance;
import com.worknest.entity.LeaveRequest;

import java.util.List;

public interface LeaveService {

    LeaveRequest applyLeave(LeaveApplyDto dto);

    LeaveRequest approveLeave(Long leaveRequestId, LeaveActionDto dto);

    LeaveRequest rejectLeave(Long leaveRequestId, LeaveActionDto dto);

    List<LeaveRequest> getEmployeeLeaveHistory(Long employeeId);

    List<LeaveRequest> getPendingLeavesForManager(Long managerId);

    List<LeaveBalance> getEmployeeLeaveBalance(Long employeeId);
}
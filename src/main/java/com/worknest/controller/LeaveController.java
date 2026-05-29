package com.worknest.controller;

import com.worknest.dto.LeaveActionDto;
import com.worknest.dto.LeaveApplyDto;
import com.worknest.entity.LeaveBalance;
import com.worknest.entity.LeaveRequest;
import com.worknest.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/apply")
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveApplyDto dto) {
        return leaveService.applyLeave(dto);
    }

    @PutMapping("/{leaveRequestId}/approve")
    public LeaveRequest approveLeave(
            @PathVariable Long leaveRequestId,
            @RequestBody LeaveActionDto dto
    ) {
        return leaveService.approveLeave(leaveRequestId, dto);
    }

    @PutMapping("/{leaveRequestId}/reject")
    public LeaveRequest rejectLeave(
            @PathVariable Long leaveRequestId,
            @RequestBody LeaveActionDto dto
    ) {
        return leaveService.rejectLeave(leaveRequestId, dto);
    }

    @GetMapping("/employee/{employeeId}/history")
    public List<LeaveRequest> getEmployeeLeaveHistory(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveHistory(employeeId);
    }

    @GetMapping("/employee/{employeeId}/balance")
    public List<LeaveBalance> getEmployeeLeaveBalance(@PathVariable Long employeeId) {
        return leaveService.getEmployeeLeaveBalance(employeeId);
    }

    @GetMapping("/manager/{managerId}/pending")
    public List<LeaveRequest> getPendingLeavesForManager(@PathVariable Long managerId) {
        return leaveService.getPendingLeavesForManager(managerId);
    }
}
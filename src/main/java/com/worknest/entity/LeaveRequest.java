package com.worknest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="leave_type_id", nullable = false)
    private LeaveType leaveType;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    private String reason;

    private String status;

    @Column(name="rejection_reason")
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name="approved_by")
    private Employee approvedBy;
}
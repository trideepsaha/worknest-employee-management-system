package com.worknest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="leave_type_id", nullable = false)
    private LeaveType leaveType;

    @Column(name="total_leaves")
    private Integer totalLeaves;

    @Column(name="used_leaves")
    private Integer usedLeaves;

    @Column(name="remaining_leaves")
    private Integer remainingLeaves;
}
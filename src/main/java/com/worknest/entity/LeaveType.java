package com.worknest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="leave_name", nullable = false, unique = true)
    private String leaveName;

    @Column(name="default_days", nullable = false)
    private Integer defaultDays;
}
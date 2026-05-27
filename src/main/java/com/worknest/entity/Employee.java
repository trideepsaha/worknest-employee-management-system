package com.worknest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Column(name="full_name", nullable = false)
    private String fullName;

    private String phone;

    private String designation;

    @Column(name="joining_date")
    private LocalDate joiningDate;

    private Double salary;

    private String status;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}

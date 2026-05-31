package com.worknest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceReportDto {

    private Long employeeId;

    private String employeeName;

    private int totalDays;

    private int presentDays;

    private int halfDays;

    private int absentDays;

    private int lateDays;

    private double totalWorkingHours;
}
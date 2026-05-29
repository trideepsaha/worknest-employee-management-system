package com.worknest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveActionDto {

    private Long managerId;

    private String rejectionReason;
}
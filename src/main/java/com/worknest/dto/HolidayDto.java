package com.worknest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayDto {

    @NotBlank(message = "Holiday name is required")
    private String holidayName;

    @NotNull(message = "Holiday date is required")
    private LocalDate holidayDate;

    @NotBlank(message = "Holiday type is required")
    private String holidayType;
}